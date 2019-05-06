package com.jex.official.service;

import com.jex.official.common.Constant;
import com.jex.official.common.DateTimeUtils;
import com.jex.official.config.OfficialConfig;
import com.jex.official.entity.db.KeywordReply;
import com.jex.official.entity.db.Official;
import com.jex.official.entity.db.Payment;
import com.jex.official.entity.db.SubscribeReply;
import com.jex.official.repository.OfficialRepository;
import com.jex.official.sdk.wechat.WechatHepler;
import com.jex.official.sdk.wechat.WechatSDK;
import com.jex.official.sdk.wechat.XMLParse;
import com.jex.official.sdk.wechat.dto.OAuthAccessTokenResponse;
import com.jex.official.sdk.wechat.dto.SNSUserInfoResponse;
import com.jex.official.sdk.wechat.model.WechatMessage;
import com.jex.official.task.OfficialTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WechatOfficialService {
    private final OfficialConfig officialConfig;
    private final OfficialTask officialTask;
    private final OfficialRepository officialRepository;
    private final SubscribeReplyService subscribeReplyService;
    private final KeywordReplyService keywordReplyService;
    private final PaymentService paymentService;

    @Autowired
    public WechatOfficialService(OfficialConfig officialConfig,
                                 OfficialTask officialTask,
                                 OfficialRepository officialRepository,
                                 SubscribeReplyService subscribeReplyService,
                                 KeywordReplyService keywordReplyService,
                                 PaymentService paymentService){
        this.officialConfig = officialConfig;
        this.officialTask = officialTask;
        this.officialRepository = officialRepository;
        this.subscribeReplyService = subscribeReplyService;
        this.keywordReplyService = keywordReplyService;
        this.paymentService = paymentService;
    }

    public boolean checkSignature(String appId, String signature, String timestamp, String nonce) throws Exception{
        Optional<Official> optionalOfficial = this.officialRepository.findOneByAppId(appId);
        if(!optionalOfficial.isPresent()){
            return false;
        }
        Official official = optionalOfficial.get();
        WechatHepler helper = new WechatHepler(official.getAppId(), official.getToken(), official.getAesKey());
        String sign = helper.getSHA1(timestamp, nonce, "");
        if(signature.equals(sign)){
            return true;
        }
        return false;
    }

    public String acceptMessage(String xml) throws Exception{
        Map<String, String> body = XMLParse.xmlParse(xml);
        String toUserName = body.get("ToUserName");
        // 获取出对应的公众号
        Optional<Official> optionalOfficial = this.officialRepository.findOneByAccount(toUserName);
        if(!optionalOfficial.isPresent()){
            return "ERROR";
        }
        Official official = optionalOfficial.get();
        String encrypt = body.get("Encrypt");
        WechatHepler helper = new WechatHepler(official.getAppId(), official.getToken(), official.getAesKey());
        WechatMessage message = helper.decrypt(encrypt);
        Map<String, String> data = message.getData();
        switch (data.get("MsgType")){
            case WechatMessage.MSG_TYPE_EVENT:
                return messageEvent(official, data);
            case WechatMessage.MSG_TYPE_TEXT:
                return messageText(official, data);
            default:
                break;
        }
        return "SUCCESS";
    }

    public ModelAndView oauthCallback(HttpServletRequest request, String code, String state) throws Exception{
        URI uri = new URI(state);
        String appId = uri.getScheme();
        Optional<Official> optionalOfficial = officialRepository.findOneByAppId(appId);
        if(!optionalOfficial.isPresent()){
            throw new Exception("official not exists");
        }
        Official official = optionalOfficial.get();
        WechatSDK sdk = new WechatSDK(official.getAppId(), official.getAppSecret());
        OAuthAccessTokenResponse tokenResponse = sdk.getOAuthAccessToken(code);
        if(tokenResponse.getErrCode() != 0){
            throw new Exception("get oauth token error: " + tokenResponse.getErrMsg());
        }
        SNSUserInfoResponse userInfoResponse =  sdk.getSNSUserInfo(tokenResponse.getAccessToken(), tokenResponse.getOpenId());
        if(userInfoResponse.getErrCode() != 0){
            throw new Exception("get sns user info error: " + userInfoResponse.getErrMsg());
        }

        this.officialTask.asyncUpdateWechatUser(official, userInfoResponse);

        String url = String.format("/%s%s", uri.getHost(), uri.getPath());
        if(StringUtils.hasText(uri.getQuery())){
            url += "?" + uri.getQuery();
        }
        request.getSession().setAttribute(Constant.SESSION_KEY_USER_ID, userInfoResponse.getOpenId());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:" + url);

        return mv;
    }

    public ModelAndView baseAuthCallback(HttpServletRequest request, String code, String state) throws Exception {
        URI uri = new URI(state);
        Payment payment = this.paymentService.findOnePayment();
        WechatSDK sdk = new WechatSDK(payment.getAppId(), payment.getAppSecret());
        OAuthAccessTokenResponse tokenResponse = sdk.getOAuthAccessToken(code);
        if (tokenResponse.getErrCode() != 0) {
            throw new Exception("get oauth token error: " + tokenResponse.getErrMsg());
        }
        SNSUserInfoResponse userInfoResponse = sdk.getSNSUserInfo(tokenResponse.getAccessToken(), tokenResponse.getOpenId());
        if (userInfoResponse.getErrCode() != 0) {
            throw new Exception("get sns user info error: " + userInfoResponse.getErrMsg());
        }
        request.getSession().setAttribute(Constant.SESSION_PAY_USER_ID, userInfoResponse.getOpenId());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:" + uri);
        return mv;
    }

    private String messageEvent(Official official, Map<String, String> data) throws Exception{
        switch (data.get("Event")){
            case WechatMessage.MSG_EVENT_SUBSCRIBE:
                return subscribeEventAction(official, data);
            case WechatMessage.MSG_EVENT_UNSUBSCRIBE:
                return unsubscribeEventAction(official, data);
            case WechatMessage.MSG_EVENT_CLICK:
                return clickEventAction(official, data);
            default:
                break;
        }
        return "SUCCESS";
    }

    private String messageText(Official official, Map<String, String> data) throws Exception{
        String timestamp = String.valueOf(DateTimeUtils.getTimestamp(LocalDateTime.now()));
        String keyword = data.get("Content");
        String fromUserName = data.get("FromUserName");
        KeywordReply keywordReply = this.keywordReplyService.getKeywordReply(official, keyword);

        Map<String, String> resMsg = new HashMap<>();
        resMsg.put("FromUserName", official.getAccount());
        resMsg.put("ToUserName", fromUserName);
        resMsg.put("CreateTime", timestamp);
        resMsg.put("MsgType", WechatMessage.MSG_TYPE_TEXT);
        if(keywordReply == null){
            resMsg.put("Content", "");
        }else{
            resMsg.put("Content", keywordReply.getContent());
        }
        String resMsgXml = XMLParse.xmlFormat(resMsg, true); //生成明文xml

        String randomStr = WechatHepler.getRandomStr();
        String nonce = WechatHepler.getRandomStr();
        WechatHepler helper = new WechatHepler(official.getAppId(), official.getToken(), official.getAesKey());
        String encryptStr = helper.encrypt(randomStr, resMsgXml);  //加密
        String signature = helper.getSHA1(timestamp, nonce, encryptStr); //签名

        String resultXml = XMLParse.generate(encryptStr, signature, timestamp, nonce);
        return resultXml;
    }

    /**
     * 关注事件
     * @return
     */
    private String subscribeEventAction(Official official, Map<String, String> data) throws Exception{
        this.officialTask.asyncUpdateWechatUser(official, data);

        String userOpenId = data.get("FromUserName");
        String timestamp = String.valueOf(DateTimeUtils.getTimestamp(LocalDateTime.now()));
        String randomStr = WechatHepler.getRandomStr();
        String nonce = WechatHepler.getRandomStr();
        SubscribeReply subscribeReply = subscribeReplyService.findSubReplyByOfficialId(official.getId());

        Map<String, String> resMsg = new HashMap<>();
        resMsg.put("FromUserName", official.getAccount());
        resMsg.put("ToUserName", userOpenId);
        resMsg.put("CreateTime", timestamp);
        resMsg.put("MsgType", WechatMessage.MSG_TYPE_TEXT);
        resMsg.put("Content", subscribeReply.getContent());
        String resMsgXml = XMLParse.xmlFormat(resMsg, true); //生成明文xml

        WechatHepler helper = new WechatHepler(official.getAppId(), official.getToken(), official.getAesKey());
        String encryptStr = helper.encrypt(randomStr, resMsgXml);  //加密
        String signature = helper.getSHA1(timestamp, nonce, encryptStr); //签名

        String resultXml = XMLParse.generate(encryptStr, signature, timestamp, nonce);
        return resultXml;
    }

    /**
     * 取关事件
     * @return
     */
    private String unsubscribeEventAction(Official official, Map<String, String> data) throws Exception{
        this.officialTask.asyncUnsubscribeUser(official, data);
        return "SUCCESS";
    }

    /**
     * 点击事件
     * @return
     */
    private String clickEventAction(Official official, Map<String, String> data) throws Exception{
        String timestamp = String.valueOf(DateTimeUtils.getTimestamp(LocalDateTime.now()));
        String keyword = data.get("EventKey");
        String fromUserName = data.get("FromUserName");
        KeywordReply keywordReply = this.keywordReplyService.getKeywordReply(official, keyword);

        Map<String, String> resMsg = new HashMap<>();
        resMsg.put("FromUserName", official.getAccount());
        resMsg.put("ToUserName", fromUserName);
        resMsg.put("CreateTime", timestamp);
        resMsg.put("MsgType", WechatMessage.MSG_TYPE_TEXT);
        if(keywordReply == null){
            resMsg.put("Content", "");
        }else{
            resMsg.put("Content", keywordReply.getContent());
        }
        String resMsgXml = XMLParse.xmlFormat(resMsg, true); //生成明文xml

        String randomStr = WechatHepler.getRandomStr();
        String nonce = WechatHepler.getRandomStr();
        WechatHepler helper = new WechatHepler(official.getAppId(), official.getToken(), official.getAesKey());
        String encryptStr = helper.encrypt(randomStr, resMsgXml);  //加密
        String signature = helper.getSHA1(timestamp, nonce, encryptStr); //签名

        String resultXml = XMLParse.generate(encryptStr, signature, timestamp, nonce);
        return resultXml;
    }
}

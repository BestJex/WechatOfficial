package com.jex.official.sdk.wechat;

import com.alibaba.fastjson.JSONObject;
import com.jex.official.common.RestClient;
import com.jex.official.sdk.wechat.dto.*;
import com.jex.official.sdk.wechat.model.ValueExpireInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WechatSDK {
    private final static Logger logger = LoggerFactory.getLogger(WechatSDK.class);

    private final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    private final static String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
    private final static String OAUTH_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
    private final static String OAUTH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    private final static String SNS_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
    private final static String GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
    private final static String GET_OFFICIAL_MENU = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=%s";
    private final static String DEL_OFFICIAL_MENU = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s";
    private final static String CREATE_OFFICIAL_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";


    private String appId;
    private String appSecret;

    private static Object lockObj = new Object();
    private static Object jsapiLockObj = new Object();
    private static Map<String, ValueExpireInfo> jsapiTicket = new ConcurrentHashMap<>();
    private static Map<String, String> accessTokenMap = new ConcurrentHashMap<>();
    private static Map<String, LocalDateTime> expireTimeMap = new ConcurrentHashMap<>();

    public WechatSDK(String appId, String appSecret){
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public static String getAccessToken2(String appId, String appSecret){
        return "13_vy2aQMvzHNm8dI1YIdQLhz8QR0cBdvWYdL6JAbb6yiLUfqzhpqmv4In92KqDTrn72EYlNDgXryazY8a9ZdDwUX1R_No9Q05wkMZ8UJoBZHjXogc6oDDbvkkVEtJhbyHCx1RO97kGzbjxfbWjFZEiAGAJDI";
    }
    public static String getAccessToken(String appId, String appSecret){
        if(accessTokenMap.containsKey(appId) || expireTimeMap.containsKey(appId)){
            LocalDateTime expireTime = expireTimeMap.get(appId);
            if(StringUtils.hasText(accessTokenMap.get(appId))
                    && expireTime.compareTo(LocalDateTime.now()) > 0){
                return accessTokenMap.get(appId);
            }
        }
        synchronized (lockObj) {
            LocalDateTime now = LocalDateTime.now();
            String url = String.format(ACCESS_TOKEN_URL, appId, appSecret);
            AccessTokenResponse response = RestClient.get(url, AccessTokenResponse.class);
            String accessToken = response.getAccessToken();
            LocalDateTime expireTime = now.plusSeconds(response.getExpiresIn());
            accessTokenMap.put(appId, accessToken);
            expireTimeMap.put(appId, expireTime);
            System.out.println(accessToken);
            return accessToken;
        }
    }

    public static String getAuthorizeUrl(String appId, String redirectUri, String scope, String state){
        String uri = null;
        try{
            uri = URLEncoder.encode(redirectUri, "utf-8");
        }catch(Exception ex){
            uri = redirectUri;
        }
        return String.format(OAUTH_AUTHORIZE_URL, appId, uri, scope, state);
    }

    /**
     * 检查用户是否已关注
     * @param unionId
     * @return
     */
    public boolean checkSubscribe(String unionId){
        String accessToken = WechatSDK.getAccessToken(this.appId, this.appSecret);
        String url = String.format(USER_INFO_URL, accessToken, unionId);
        UserInfoResponse response = RestClient.get(url, UserInfoResponse.class);
        if(response.getSubscribe() == 1){
            return true;
        }else{
            return false;
        }

    }

    /**
     * 获取用户信息
     * @param openId
     */
    public UserInfoResponse getUserInfo(String openId){
        String accessToken = WechatSDK.getAccessToken(this.appId, this.appSecret);
        String url = String.format(USER_INFO_URL, accessToken, openId);
        UserInfoResponse response = RestClient.get(url, UserInfoResponse.class);
        return response;
    }

    public OAuthAccessTokenResponse getOAuthAccessToken(String code){
        String url = String.format(OAUTH_ACCESS_TOKEN_URL, this.appId, this.appSecret, code);
        String response = RestClient.get(url, String.class);
        OAuthAccessTokenResponse tokenResponse = JSONObject.parseObject(response, OAuthAccessTokenResponse.class);
        return tokenResponse;
    }

    public SNSUserInfoResponse getSNSUserInfo(String accessToken, String openId){
        String url = String.format(SNS_USER_INFO_URL, accessToken, openId);
        String response = RestClient.get(url, String.class);
        SNSUserInfoResponse infoResponse = JSONObject.parseObject(response, SNSUserInfoResponse.class);
        return infoResponse;
    }

    /**
     * 获取公众号菜单
     *
     */
    public String getOfficialMenu(){
        String accessToken = WechatSDK.getAccessToken(this.appId, this.appSecret);
        String url = String.format(GET_OFFICIAL_MENU, accessToken);
        String response = RestClient.get(url, String.class);
        return response;
    }

    /**
     * 删除公众号菜单
     *
     */
    public String delOfficialMenu(){
        String accessToken = WechatSDK.getAccessToken(this.appId, this.appSecret);
        String url = String.format(DEL_OFFICIAL_MENU, accessToken);
        String response = RestClient.get(url, String.class);
        return response;
    }


    /**
     * 保存公众号菜单
     *
     */
    public String saveOfficialMenu(String menu){
        String accessToken = WechatSDK.getAccessToken(this.appId, this.appSecret);
        String url = String.format(CREATE_OFFICIAL_MENU, accessToken);
        String response = RestClient.postJSON(url, menu, String.class);
        return response;
    }

    public String getJsapiTicket(){
        return "HoagFKDcsGMVCIY2vOjf9oUmc-ai4R7k1vPA75aWOUjTAnmksce1cIzlRUdbJx-zogU6Ohd_Doe4WVCg_vezEg";
    }

    public String getJsapiTicket2(){
        if(jsapiTicket.containsKey(this.appId)){
            ValueExpireInfo info = jsapiTicket.get(this.appId);
            if(StringUtils.hasText(info.getValue())
                    && info.getExpireTime().compareTo(LocalDateTime.now()) > 0){
                return info.getValue();
            }
        }
        synchronized (jsapiLockObj){
            LocalDateTime now = LocalDateTime.now();
            String accessToken = WechatSDK.getAccessToken(this.appId, this.appSecret);
            String url = String.format(GET_TICKET_URL, accessToken);
            TicketResponse response = RestClient.get(url, TicketResponse.class);
            ValueExpireInfo temp = new ValueExpireInfo();
            temp.setValue(response.getTicket());
            temp.setExpireTime(now.plusSeconds(response.getExpiresIn()));
            jsapiTicket.put(this.appId, temp);
            logger.info(response.getTicket());
            return response.getTicket();
        }
    }
}

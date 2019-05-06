package com.jex.official.service;

import com.jex.official.common.Constant;
import com.jex.official.common.DateTimeUtils;
import com.jex.official.common.IpUtils;
import com.jex.official.config.OfficialConfig;
import com.jex.official.entity.db.Order;
import com.jex.official.entity.db.Payment;
import com.jex.official.sdk.wechat.WechatHepler;
import com.jex.official.sdk.wechat.WechatPaySDK;
import com.jex.official.sdk.wechat.WechatSDK;
import com.jex.official.sdk.wechat.XMLParse;
import com.jex.official.sdk.wechat.model.UnifiedorderPay;
import com.jex.official.sdk.wechat.model.WechatPay;
import com.jex.official.service.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;


@Service
public class WechatPayService {

    private final OfficialConfig officialConfig;
    private final OrderService orderService;
    private final PaymentService paymentService;

    @Autowired
    public WechatPayService(OfficialConfig officialConfig,
                            OrderService orderService,
                            PaymentService paymentService) {
        this.officialConfig = officialConfig;
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    public ModelAndView payAuth(String orderId)  {
        ModelAndView mv = new ModelAndView();
        String scope = "snsapi_base";
        String state = String.format("/order/write/%s", orderId);
        String uri = officialConfig.WEB_HOST + "/official/baseAuth";
        Payment payment = this.paymentService.findOnePayment();
        String url = WechatSDK.getAuthorizeUrl(payment.getAppId(), uri, scope, state);
        mv.setViewName("redirect:" + url);
        return mv;
    }


    public Map<String, String> pay(HttpServletRequest request, OrderRequest orderModel,Order order) throws Exception{
        String openId =  String.valueOf(request.getSession().getAttribute(Constant.SESSION_PAY_USER_ID));
        if(StringUtils.isEmpty(openId)){
            throw new Exception("not auth");
        }

        String name = orderModel.getName();
        String phone = orderModel.getPhone();
        String address = orderModel.getAddress();

        order.setOpenId(openId);
        order.setName(name);
        order.setPhone(phone);
        order.setAddress(address);
        this.orderService.updateOrder(order);

        //获取请求ip地址
        String ip = IpUtils.getRequestIp(request);

        UnifiedorderPay unifiedorderPay = new UnifiedorderPay();
        Payment payment = this.paymentService.findOnePayment();
        unifiedorderPay.setAppId(payment.getAppId());
        unifiedorderPay.setBody("活动订单支付");
        unifiedorderPay.setMchId(payment.getMchId());
        unifiedorderPay.setNonceStr( WechatHepler.getRandomStr());
        unifiedorderPay.setOpenId(order.getOpenId());
        unifiedorderPay.setOutTradeNo(order.getOrderId());
        unifiedorderPay.setSpbillCreateIp(ip);
        unifiedorderPay.setTotalFee(String.valueOf(order.getPrice()));
        unifiedorderPay.setTradeType("JSAPI");
        unifiedorderPay.setNotifyUrl(officialConfig.WEB_HOST + payment.getNotifyUrl());
        unifiedorderPay.setPayKey(payment.getPayKey());

        String xmlStr = WechatPaySDK.unifiedorderPay(unifiedorderPay);

        String prepay_id = "";    //预支付id
        if (xmlStr.indexOf("SUCCESS") != -1) {
            Map<String, String> map =  XMLParse.xmlParse(xmlStr);
            prepay_id =  map.get("prepay_id");
        }

        WechatPay wechatPay = new WechatPay();
        wechatPay.setAppId(payment.getAppId());
        wechatPay.setTimeStamp(String.valueOf(DateTimeUtils.getTimestamp(LocalDateTime.now())));
        wechatPay.setNonceStr( WechatHepler.getRandomStr());
        wechatPay.setSignType("MD5");
        wechatPay.setPackageName("prepay_id=" + prepay_id);
        wechatPay.setPayKey(payment.getPayKey());

        Map<String, String> payMap = WechatPaySDK.wechatPay(wechatPay);

        return payMap;

    }


}

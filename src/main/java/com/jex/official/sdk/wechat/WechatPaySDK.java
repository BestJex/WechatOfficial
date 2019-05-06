package com.jex.official.sdk.wechat;


import com.jex.official.common.HttpRequest;
import com.jex.official.sdk.wechat.model.UnifiedorderPay;
import com.jex.official.sdk.wechat.model.WechatPay;

import java.util.HashMap;
import java.util.Map;

public class WechatPaySDK {

    // 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
    private static String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";


    public static String unifiedorderPay(UnifiedorderPay unifiedorderPay) throws Exception {


        //拼接统一下单地址参数
        Map<String, String> paraMap = new HashMap<String, String>();

        paraMap.put("appid", unifiedorderPay.getAppId());
        paraMap.put("body", unifiedorderPay.getBody());
        paraMap.put("mch_id", unifiedorderPay.getMchId());
        paraMap.put("nonce_str", unifiedorderPay.getNonceStr());
        paraMap.put("openid", unifiedorderPay.getOpenId());
        paraMap.put("out_trade_no", unifiedorderPay.getOutTradeNo());  //订单号
        paraMap.put("spbill_create_ip", unifiedorderPay.getSpbillCreateIp());
        paraMap.put("total_fee", unifiedorderPay.getTotalFee());
        paraMap.put("trade_type", unifiedorderPay.getTradeType());
        paraMap.put("notify_url", unifiedorderPay.getNotifyUrl());// 此路径是微信服务器调用支付结果通知

        String sign = WechatHepler.generateMD5Signature(paraMap, unifiedorderPay.getPayKey());
        paraMap.put("sign", sign);

        String xml = XMLParse.xmlFormat(paraMap, true);

        String xmlStr = HttpRequest.sendPost(UNIFIEDORDER_URL, xml); //发送post请求"统一下单接口"返回预支付id:prepay_id
        return xmlStr;
    }


    public static Map<String, String> wechatPay(WechatPay wechatPay) throws Exception {

        Map<String, String> payMap = new HashMap<String, String>();
        payMap.put("appId", wechatPay.getAppId());
        payMap.put("timeStamp", wechatPay.getTimeStamp());
        payMap.put("nonceStr", wechatPay.getNonceStr());
        payMap.put("signType", wechatPay.getSignType());
        payMap.put("package", wechatPay.getPackageName());
        String paySign = WechatHepler.generateMD5Signature(payMap, wechatPay.getPayKey());
        payMap.put("paySign", paySign);
        return payMap;

    }
}


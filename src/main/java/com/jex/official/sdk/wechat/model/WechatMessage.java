package com.jex.official.sdk.wechat.model;

import java.util.Map;

public class WechatMessage {
    public final static String MSG_TYPE_TEXT = "text";
    public final static String MSG_TYPE_IMAGE = "image";
    public final static String MSG_TYPE_VOICE = "voice";
    public final static String MSG_TYPE_VIDEO = "video";
    public final static String MSG_TYPE_SHORT_VIDEO = "shortvideo";
    public final static String MSG_TYPE_LOCATION = "location";
    public final static String MSG_TYPE_LINK = "link";
    public final static String MSG_TYPE_EVENT = "event";

    public final static String MSG_EVENT_SUBSCRIBE = "subscribe";
    public final static String MSG_EVENT_UNSUBSCRIBE = "unsubscribe";
    public final static String MSG_EVENT_LOCATION = "LOCATION";
    public final static String MSG_EVENT_CLICK = "CLICK";
    public final static String MSG_EVENT_VIEW = "VIEW";

    private String appId;
    private String nonce;
    private Map<String, String> data;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}

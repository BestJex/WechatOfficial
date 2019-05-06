package com.jex.official.sdk.wechat.model;

import java.time.LocalDateTime;

public class ValueExpireInfo {
    private String value = "";
    private LocalDateTime expireTime = null;

    public String getValue() {
        return value;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}

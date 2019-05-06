package com.jex.official.sdk.wechat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OAuthAccessTokenResponse implements Serializable {
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "expires_in")
    private int expiresIn;
    @JsonProperty(value = "refresh_token")
    private String refreshToken;
    @JsonProperty(value = "openid")
    private String openId;
    @JsonProperty(value = "scope")
    private String scope;
    @JsonProperty(value = "unionid")
    private String unionId;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "errcode")
    private int errCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "errmsg")
    private String errMsg;

    public String getAccessToken() {
        return accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public String getScope() {
        return scope;
    }

    public String getUnionId() {
        return unionId;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}

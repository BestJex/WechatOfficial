package com.jex.official.sdk.wechat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

public class AccessTokenResponse implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "expires_in")
    private int expiresIn;
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

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String toString(){
        ObjectMapper mapperObj = new ObjectMapper();
        try {
            return mapperObj.writeValueAsString(this);
        }catch (Exception ex){
            return super.toString();
        }
    }
}

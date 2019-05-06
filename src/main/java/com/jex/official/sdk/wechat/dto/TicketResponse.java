package com.jex.official.sdk.wechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketResponse {
    @JsonProperty(value = "ticket")
    private String ticket;
    @JsonProperty(value = "expires_in")
    private int expiresIn;
    @JsonProperty(value = "errcode")
    private int errCode;
    @JsonProperty(value = "errmsg")
    private String errMsg;

    public String getTicket() {
        return ticket;
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

    public void setTicket(String ticket) {
        this.ticket = ticket;
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
}

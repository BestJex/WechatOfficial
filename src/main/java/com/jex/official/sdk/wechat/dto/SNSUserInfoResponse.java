package com.jex.official.sdk.wechat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SNSUserInfoResponse implements Serializable {
    @JsonProperty(value = "openid")
    private String openId;
    @JsonProperty(value = "nickname")
    private String nickname;
    @JsonProperty(value = "sex")
    private int sex;
    @JsonProperty(value = "country")
    private String country;
    @JsonProperty(value = "province")
    private String province;
    @JsonProperty(value = "city")
    private String city;
    @JsonProperty(value = "headimgurl")
    private String headImgUrl;
    @JsonProperty(value = "privilege")
    private String[] privilege;
    @JsonProperty(value = "unionid")
    private String unionId;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "errcode")
    private int errCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "errmsg")
    private String errMsg;

    public String getOpenId() {
        return openId;
    }

    public String getNickname() {
        return nickname;
    }

    public int getSex() {
        return sex;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public String[] getPrivilege() {
        return privilege;
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

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
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

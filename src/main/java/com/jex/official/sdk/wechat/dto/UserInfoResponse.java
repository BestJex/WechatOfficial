package com.jex.official.sdk.wechat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserInfoResponse implements Serializable {
    private int subscribe;
    @JsonProperty(value = "openid")
    private String openId;
    private String nickname;
    private int sex;
    private String city;
    private String country;
    private String province;
    private String language;
    @JsonProperty(value = "headimgurl")
    private String headImgUrl;
    @JsonProperty(value = "subscribe_time")
    private int subscribeTime;
    @JsonProperty(value = "unionid")
    private String unionId;
    private String remark;
    @JsonProperty(value = "groupid")
    private String groupId;
    @JsonProperty(value = "tagid_list")
    private String[] tagidList;
    @JsonProperty(value = "subscribe_scene")
    private String subscribeScene;
    @JsonProperty(value = "qr_scene")
    private String qrScene;
    @JsonProperty(value = "qr_scene_str")
    private String qrSceneStr;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "errcode")
    private int errCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "errmsg")
    private String errMsg;

    public int getSubscribe() {
        return subscribe;
    }

    public String getOpenId() {
        return openId;
    }

    public String getNickname() {
        return nickname;
    }

    public int getSex() {
        return sex;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getLanguage() {
        return language;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public int getSubscribeTime() {
        return subscribeTime;
    }

    public String getUnionId() {
        return unionId;
    }

    public String getRemark() {
        return remark;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getSubscribeScene() {
        return subscribeScene;
    }

    public String getQrScene() {
        return qrScene;
    }

    public String getQrSceneStr() {
        return qrSceneStr;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
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

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public void setSubscribeTime(int subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setTagidList(String[] tagidList) {
        this.tagidList = tagidList;
    }

    public void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene;
    }

    public void setQrScene(String qrScene) {
        this.qrScene = qrScene;
    }

    public void setQrSceneStr(String qrSceneStr) {
        this.qrSceneStr = qrSceneStr;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}

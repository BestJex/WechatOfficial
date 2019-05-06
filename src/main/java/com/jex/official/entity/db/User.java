package com.jex.official.entity.db;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {
    public final static int STATUS_SUBSCRIBE = 1;
    public final static int STATUS_UNSUBSCRIBE = 2;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "app_id")
    private String appId;
    @Column(name = "open_id")
    private String openId;
    @Column(name = "union_id")
    private String unionId = "";
    @Column(name = "nickname")
    private String nickname = "";
    @Column(name = "sex")
    private int sex = 0;
    @Column(name = "country")
    private String country = "";
    @Column(name = "province")
    private String province = "";
    @Column(name = "city")
    private String city = "";
    @Column(name = "language")
    private String language = "";
    @Column(name = "head_img_url")
    private String headImgUrl = "";
    @Column(name = "subscribe_Time")
    private int subscribeTime = 0;
    @Column(name = "subscribe_scene")
    private String subscribeScene = "";
    @Column(name = "status")
    private int status;
    @CreatedDate
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;
    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public int getId() {
        return id;
    }

    public String getAppId() {
        return appId;
    }

    public String getOpenId() {
        return openId;
    }

    public String getUnionId() {
        return unionId;
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

    public String getLanguage() {
        return language;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public int getSubscribeTime() {
        return subscribeTime;
    }

    public String getSubscribeScene() {
        return subscribeScene;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
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

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public void setSubscribeTime(int subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

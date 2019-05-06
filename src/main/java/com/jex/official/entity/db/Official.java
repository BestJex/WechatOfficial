package com.jex.official.entity.db;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "official")
@EntityListeners(AuditingEntityListener.class)
public class Official {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "account")
    private String account;
    @Column(name = "name")
    private String name;
    @Column(name = "app_id")
    private String appId;
    @Column(name = "app_secret")
    private String appSecret;
    @Column(name = "token")
    private String token;
    @Column(name = "aes_key")
    private String aesKey;
    @CreatedDate
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;
    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public int getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getToken() {
        return token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }
}

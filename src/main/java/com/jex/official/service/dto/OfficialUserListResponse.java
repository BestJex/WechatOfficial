package com.jex.official.service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class OfficialUserListResponse {
    private int count;
    private List<OfficialUserItemResponse>  items;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OfficialUserItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OfficialUserItemResponse> items) {
        this.items = items;
    }

    public static class OfficialUserItemResponse{
        private int id;
        private String officialName;
        private String openId;
        private String unionId;
        private String nickName;
        private int sex;
        private String country;
        private String province;
        private String city;
        private String language;
        private String headImgUrl;
        private int subscribeTime;
        private String subscribeScene;
        private int status;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime createTime;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOfficialName() {
            return officialName;
        }

        public void setOfficialName(String officialName) {
            this.officialName = officialName;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getUnionId() {
            return unionId;
        }

        public void setUnionId(String unionId) {
            this.unionId = unionId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
        }

        public int getSubscribeTime() {
            return subscribeTime;
        }

        public void setSubscribeTime(int subscribeTime) {
            this.subscribeTime = subscribeTime;
        }

        public String getSubscribeScene() {
            return subscribeScene;
        }

        public void setSubscribeScene(String subscribeScene) {
            this.subscribeScene = subscribeScene;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public LocalDateTime getCreateTime() {
            return createTime;
        }

        public void setCreateTime(LocalDateTime createTime) {
            this.createTime = createTime;
        }

        public LocalDateTime getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
        }
    }
}

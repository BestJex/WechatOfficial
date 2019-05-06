package com.jex.official.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class SubscribeReplyResponse {
    private int count;
    private List<SubscribeReplyItemResponse> items;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SubscribeReplyItemResponse> getItems() {
        return items;
    }

    public void setItems(List<SubscribeReplyItemResponse> items) {
        this.items = items;
    }

    public static class SubscribeReplyItemResponse {
        private int id;
        private int officialId;
        private String officialName;
        private String content;
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

        public int getOfficialId() {
            return officialId;
        }

        public void setOfficialId(int officialId) {
            this.officialId = officialId;
        }

        public String getOfficialName() {
            return officialName;
        }

        public void setOfficialName(String officialName) {
            this.officialName = officialName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

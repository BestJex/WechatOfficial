package com.jex.official.service.dto;

import org.springframework.util.StringUtils;

import java.io.Serializable;

public class KeywordReplyCreateRequest implements Serializable {

    private Integer[] officialIds;
    private String keyword;
    private String content;

    public Integer[] getOfficialIds() {
        return officialIds;
    }

    public void setOfficialIds(Integer[] officialIds) {
        this.officialIds = officialIds;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean validate(){
        if(this.officialIds == null || this.officialIds.length == 0
            ||!StringUtils.hasText(this.keyword) || !StringUtils.hasText(this.content)){
            return false;
        }
        return true;
    }
}

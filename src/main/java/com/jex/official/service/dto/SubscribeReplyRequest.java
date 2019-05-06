package com.jex.official.service.dto;

import java.io.Serializable;

public class SubscribeReplyRequest implements Serializable {
    private int limit;
    private int page;
    private int officialId;
    private String content;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOfficialId() {
        return officialId;
    }

    public void setOfficialId(int officialId) {
        this.officialId = officialId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

package com.jex.official.service.dto;

public class KeywordReplyListRequest {
    private int officialId;
    private String keyword = "";
    private int page;
    private int limit;

    public int getOfficialId() {
        return officialId;
    }

    public void setOfficialId(int officialId) {
        this.officialId = officialId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}

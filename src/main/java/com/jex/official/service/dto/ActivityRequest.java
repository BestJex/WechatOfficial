package com.jex.official.service.dto;

import java.io.Serializable;

public class ActivityRequest implements Serializable {
    private int limit;
    private  int page;
    private int officialId;
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOfficialId() {
        return officialId;
    }

    public void setOfficialId(int officialId) {
        this.officialId = officialId;
    }

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
}

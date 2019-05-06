package com.jex.official.service.dto;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    private String error;
    private String username;
    private String password;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

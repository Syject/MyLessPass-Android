package com.syject.data.entities;

public class LesspassSessionInfo {

    private String token;

    public LesspassSessionInfo(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return  "JWT " + getToken();
    }
}

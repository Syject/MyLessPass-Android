package com.syject.data.api;

public class UserRequest {
    final String email;
    final String password;

    public UserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

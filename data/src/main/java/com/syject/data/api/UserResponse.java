package com.syject.data.api;

public class UserResponse {
    final String email;
    final Integer id;

    public UserResponse(String email, Integer id) {
        this.email = email;
        this.id = id;
    }
}

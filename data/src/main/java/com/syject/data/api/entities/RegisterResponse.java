package com.syject.data.api.entities;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("email")
    public String email;
    @SerializedName("id")
    public Integer id;
}

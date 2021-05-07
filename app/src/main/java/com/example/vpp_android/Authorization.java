package com.example.vpp_android;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Authorization {
    @SerializedName("body")
    private String username;
    private String password;

    public Authorization(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

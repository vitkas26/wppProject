package com.example.vpp_android.domain.auth_classes;

import com.google.gson.annotations.SerializedName;

public class TelegramUrl {
    @SerializedName("redirect-url")
    private String url;

    public String getUrl() {
        return url;
    }
}

package com.example.vpp_android;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

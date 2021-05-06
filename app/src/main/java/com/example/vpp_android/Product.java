package com.example.vpp_android;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("data")
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }
}

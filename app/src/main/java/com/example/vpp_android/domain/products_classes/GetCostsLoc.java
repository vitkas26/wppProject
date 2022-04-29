package com.example.vpp_android.domain.products_classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCostsLoc {
    @SerializedName("data")
    private List<DataLoc> data;

    public List<DataLoc> getData() {
        return data;
    }
}

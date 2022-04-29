package com.example.vpp_android.domain.districts;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataDistricts {
    @SerializedName("data")
    List<Districts> districts;

    public List<Districts> getDistricts() {
        return districts;
    }
}

package com.example.vpp_android.domain.districts;

import com.google.gson.annotations.SerializedName;

public class Districts {
    @SerializedName("id")
    private Integer id;
    private String region;
    private String name;
    private String population;

    public String getRegion() {
        return region;
    }

    public String getName() {
        return name;
    }

    public String getPopulation() {
        return population;
    }

    public Integer getId() {
        return id;
    }

}

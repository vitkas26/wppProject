package com.example.vpp_android.domain.costs_classes;

import com.google.gson.annotations.SerializedName;

public class Costs {
    @SerializedName("body")
    private float consumption_rate;
    private float produced;
    private float stock_by_population;
    private float outlet_stock;
    private float price;
    private float longitude;
    private float latitude;


    public float getConsumption_rate() {
        return consumption_rate;
    }

    public float getProduced() {
        return produced;
    }

    public float getStock_by_population() {
        return stock_by_population;
    }

    public float getOutlet_stock() {
        return outlet_stock;
    }

    public float getPrice() {
        return price;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }
}

package com.example.vpp_android.domain.products_classes;

import com.google.gson.annotations.SerializedName;

public class QuestionnaireProduct {
    @SerializedName("product_id")
    private int id;
    @SerializedName("surplus")
    private Float surplus;
    @SerializedName("production")
    private Float production;
    @SerializedName("shipped")
    private Float shipped;

    public void setId(int id) {
        this.id = id;
    }

    public void setSurplus(float surplus) {
        this.surplus = surplus;
    }

    public void setProduction(float production) {
        this.production = production;
    }

    public void setShipped(float shipped) {
        this.shipped = shipped;
    }

    public int getId() {
        return id;
    }

    public float getSurplus() {
        return surplus;
    }

    public float getProduction() {
        return production;
    }

    public float getShipped() {
        return shipped;
    }
}

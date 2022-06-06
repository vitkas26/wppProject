package com.example.vpp_android.domain.products_classes;

import com.google.gson.annotations.SerializedName;

public class QuestionnaireProductOutlet {
    @SerializedName("product_id")
    private int id;
    @SerializedName("surplus")
    private Float surplus;
    @SerializedName("sales")
    private Float sales;

    public void setId(int id) {
        this.id = id;
    }

    public void setSurplus(float surplus) {
        this.surplus = surplus;
    }


    public void setShipped(float sales) {
        this.sales = sales;
    }

    public int getId() {
        return id;
    }

    public float getSurplus() {
        return surplus;
    }


    public float getShipped() {
        return sales;
    }
}

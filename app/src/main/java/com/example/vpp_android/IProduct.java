package com.example.vpp_android;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.Call;

public interface IProduct {

    @GET("products")
    Call<Product> getProduct();
}

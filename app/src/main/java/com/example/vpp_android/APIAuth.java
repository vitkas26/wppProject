package com.example.vpp_android;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIAuth {

    @POST("login")
    @FormUrlEncoded
    Call<Authorization> authUser(@Field("username") String username,
                                @Field("password") String password);
}

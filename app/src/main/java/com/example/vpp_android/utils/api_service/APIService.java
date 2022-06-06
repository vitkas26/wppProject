package com.example.vpp_android.utils.api_service;

import com.example.vpp_android.domain.UserLocation;
import com.example.vpp_android.domain.districts.DataDistricts;

import com.example.vpp_android.domain.auth_classes.TelegramUrl;
import com.example.vpp_android.domain.costs_classes.Costs;
import com.example.vpp_android.domain.costs_classes.GetCosts;
import com.example.vpp_android.domain.products_classes.Product;

import com.example.vpp_android.domain.products_classes.QuestionnaireData;
import com.example.vpp_android.domain.products_classes.QuestionnaireDataNew;
import com.example.vpp_android.domain.profile.DataProfile;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    //Get products from server
    @GET("products")
    Call<Product> getProduct();

    //Get products parent from server
    @POST("product/parents")
    @FormUrlEncoded
    Call<Product> getProductsParent(@Field("code") String code);

    //Post phone number for registration
    @POST("register")
    @FormUrlEncoded
    Call<TelegramUrl> postTel(@Field("phone_number") String phoneNumber);


    //Post data of new company
    @POST("questionnaire1/new")
    Call<JSONObject> postQuestionnaireOneNew(@Header("Authorization") String token,
                                             @Body QuestionnaireDataNew questionnaireData);

    //Post data of existing company
    @Headers("Content-Type: application/json")
    @POST("questionnaire1/1")
    Call<JSONObject> postQuestionnaireOne(@Header("Authorization") String token,
                                          @Body QuestionnaireData questionnaireData);



     //Get all history notes by region ID
    @GET("costs/history/{id}")
    Call<GetCosts> getHistory(@Path("id") int id,
                              @Header("Authorization") String token);

     //Get profile info
    @GET("costs/{location}")
     Call<DataProfile> getEmployee(@Path("location") int location,
                                   @Header("Authorization") String token);

     //Get products by ID and by location
    @GET("costs/{location}/{id}")
    Call<GetCosts> getCosts(@Path("location") int location,
                            @Path("id") int id,
                            @Header("Authorization") String token);

     //Get Districts
     @GET("districts")
     Call<DataDistricts> getDistricts(@Header("Authorization") String token);

 //Post request, send user location
        @POST("locations/check_user")
        @FormUrlEncoded
        Call<UserLocation> postUserLocation(@Header("Authorization") String token,
                                            @Field("user_longitude") Float longitude,
                                            @Field("user_latitude") Float latitude);

  /*Login to service
    @POST("login")
    @FormUrlEncoded
    Call<Authorization> authUser(@Field("username") String username,
                                 @Field("password") String password);
*/

// Post costs
        @POST("costs/{district}/{id}")
        @FormUrlEncoded
        Call<Costs> setCost(@Path("id")

                                    int id,
                            @Path("district")
                                    int district,
                            @Header("Authorization")
                                    String token,
                            @Field("consumption_rate")
                                    Float consumption_rate,
                            @Field("produced")
                                    Float produced,
                            @Field("stock_by_population")
                                    Float stock_by_population,
                            @Field("outlet_stock")
                                    Float outlet_stock,
                            @Field("price")
                                    Float price,
                            @Field("longitude")
                                    Float longitude,
                            @Field("latitude")
                                    Float latitude);

/*
    Send sms to server
    @POST("login_by_otp")
    @FormUrlEncoded
    Call<Token> sendSms(@Field("otp_code") String smsCode);
*/

}
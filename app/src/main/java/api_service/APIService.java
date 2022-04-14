package api_service;

import com.example.vpp_android.districts.DataDistricts;

import auth_classes.Authorization;
import auth_classes.TelegramUrl;
import auth_classes.Token;
import costs_classes.Costs;
import costs_classes.GetCosts;
import products_classes.Product;

import profile.DataProfile;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    //Login to service
    @POST("login")
    @FormUrlEncoded
    Call<Authorization> authUser(@Field("username") String username,
                                 @Field("password") String password);

    //Send tel to server
    @POST("register")
    @FormUrlEncoded
    Call<TelegramUrl> sendTel(@Field("phone_number") String phoneNumber);

    //Send sms to server
    @POST("login_by_otp")
    @FormUrlEncoded
    Call<Token> sendSms(@Field("otp_code") String smsCode);

    //getProduct
    @GET("products")
    Call<Product> getProduct();

    //getDistricts
    @GET("districts")
    Call<DataDistricts> getDistricts(@Header("Authorization") String token);

    //getWorkerInfo
    @GET("profiles")
    Call<DataProfile> getEmployee(@Header("Authorization") String token);

    //setCosts
    @POST("costs/{district}/{id}")
    @FormUrlEncoded
    Call<Costs> setCost(@Path("id") int id,
                        @Path("district") int district,
                        @Header("Authorization") String token,
                        @Field("consumption_rate") Float consumption_rate,
                        @Field("produced") Float produced,
                        @Field("stock_by_population") Float stock_by_population,
                        @Field("outlet_stock") Float outlet_stock,
                        @Field("price") Float price,
                        @Field("longitude") Float longitude,
                        @Field("latitude") Float latitude);

    //Get products by ID and by location
    @GET("costs/{location}/{id}")
    Call<GetCosts> getCosts(@Path("location") int location,
                            @Path("id") int id,
                            @Header("Authorization") String token);
    //Get all history notes by region ID
    @GET("costs/history/{id}")
    Call<GetCosts> getHistory(@Path("id") int id,
                            @Header("Authorization") String token);

}

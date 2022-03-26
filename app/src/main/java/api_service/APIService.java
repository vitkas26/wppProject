package api_service;

import android.renderscript.ScriptGroup;

import com.example.vpp_android.InputData;
import com.example.vpp_android.R;

import auth_classes.Authorization;
import auth_classes.PhoneNumber;
import auth_classes.Token;
import costs_classes.Costs;
import costs_classes.GetCosts;
import costs_classes.MainCostsData;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import products_classes.Product;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface APIService {
    //Login to service
    @POST("login")
    @FormUrlEncoded
    Call<Authorization> authUser(@Field("username") String username,
                                 @Field("password") String password);

    //Send tel to server
    @POST("tel")
    @FormUrlEncoded
    Call<PhoneNumber> sendTel(@Field("tel") String phoneNumber);

    //Send sms to server
    @POST("sms")
    @FormUrlEncoded
    Call<Token> sendSms(@Field("sms_code") String smsCode);

    //getProduct
    @GET("products")
    Call<Product> getProduct();

    //getWorkerInfo
    @GET("costs")
    Call<GetCosts> getCosts(@Header("Authorization") String token);


    @POST("costs/{id}")
    @FormUrlEncoded
    Call<Costs> setCost(@Path("id") int id,
                        @Header("Authorization") String token,
                        @Field("consumption_rate") Float consumption_rate,
                        @Field("produced") Float produced,
                        @Field("stock_by_population") Float stock_by_population,
                        @Field("outlet_stock") Float outlet_stock,
                        @Field("price") Float price,
                        @Field("longitude") Float longitude,
                        @Field("latitude") Float latitude);

    @GET("costs/{id}")
    Call<GetCosts> getCosts(@Path("id") int id,
                            @Header("Authorization") String token);
}

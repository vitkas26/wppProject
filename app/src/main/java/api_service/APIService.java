package api_service;

import android.renderscript.ScriptGroup;

import com.example.vpp_android.InputData;
import com.example.vpp_android.R;

import auth_classes.Authorization;
import costs_classes.Costs;
import costs_classes.GetCosts;
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

    //getProduct
    @GET("products")
    Call<Product> getProduct();

//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("costs/1")
//    Call<ResponseBody> getToken(@Header("Authorization") String auth);

    @POST("costs/{id}")
    @FormUrlEncoded
    Call<Costs> setCost(@Path("id") int id,
                        @Field("consumption_rate") Float consumption_rate,
                        @Field("produced") Float produced,
                        @Field("stock_by_population") Float stock_by_population,
                        @Field("outlet_stock") Float outlet_stock,
                        @Field("price") Float price,
                        @Field("longitude") Float longitude,
                        @Field("latitude") Float latitude);

    @GET("costs/{id}")
    Call<GetCosts> getCosts(@Path("id") int id);
}

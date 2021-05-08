package api_service;

import auth_classes.Authorization;
import costs_classes.Costs;
import products_classes.Product;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    //Login to service
    @POST("login")
    @FormUrlEncoded
    Call<Authorization> authUser(@Field("username") String username,
                                 @Field("password") String password);

    //getProduct
    @GET("products")
    Call<Product> getProduct();

    @POST("costs")
    Call<Costs> setCost(@Field("consumption_rate") float consumption_rate,
                        @Field("produced") float produced,
                        @Field("stock_by_population") float stock_by_population,
                        @Field("outlet_stock") float outlet_stock,
                        @Field("price") float price,
                        @Field("longitude") float longitude,
                        @Field("latitude") float latitude,
                        @Field("id") float id);
}

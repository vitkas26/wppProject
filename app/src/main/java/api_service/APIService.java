package api_service;

import auth_classes.Authorization;
import costs_classes.Costs;
import costs_classes.GetCosts;
import products_classes.GetCostsLoc;
import products_classes.Product;

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

    //getProduct
    @GET("products")
    Call<Product> getProduct();

    @GET("districts")
    Call<GetCostsLoc> getLoc(
            @Header("Authorization") String token);


    @POST("costs/{location}/{id}")
    @FormUrlEncoded
    Call<Costs> setCost(@Path("location") int location,
                        @Path("id") int id,
                        @Header("Authorization") String token,
                        @Field("consumption_rate") Float consumption_rate,
                        @Field("produced") Float produced,
                        @Field("stock_by_population") Float stock_by_population,
                        @Field("outlet_stock") Float outlet_stock,
                        @Field("price") Float price,
                        @Field("longitude") Float longitude,
                        @Field("latitude") Float latitude);

    @GET("costs/{location}/{id}")
    Call<GetCosts> getCosts(@Path("location") int location,
                            @Path("id") int id,
                            @Header("Authorization") String token);

}

package api_service;

import com.example.vpp_android.InputData;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static OkHttpClient client;

    public static Retrofit getClient(String baseUrl){
       client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Token " + "f581c86a98d9b9d059f0a7cfd6161eb6721e4e7a")
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

package com.example.vpp_android;

public class APIUtils {
    private APIUtils() {}

    public static final String BASE_URL = "http://212.42.106.73/api/v1/";

    public static APIAuth getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIAuth.class);
    }
}

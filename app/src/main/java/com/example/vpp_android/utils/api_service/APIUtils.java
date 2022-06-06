package com.example.vpp_android.utils.api_service;

public class APIUtils {
    private APIUtils() {}

//    public static final String BASE_URL = "http://212.42.106.72/api/v1/";
    public static final String BASE_URL = "https://b96e-212-42-106-77.in.ngrok.io/api/v1/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}

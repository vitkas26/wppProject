package com.example.vpp_android;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import savingdata_class.Account;


public class TelegramWebViewClient extends WebViewClient {
    onFinish onFinish;

    public TelegramWebViewClient (onFinish onFinish){
        this.onFinish = onFinish;
    }

    public interface onFinish{
        void getToken(WebResourceRequest request);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.getUrl().toString());
        Log.d("@@@", "shouldOverrideUrlLoading: " + request.getUrl().toString());
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

        if (request.getUrl().toString().contains("token")) {
            this.onFinish.getToken(request);
        }
        return super.shouldInterceptRequest(view, request);
    }


}

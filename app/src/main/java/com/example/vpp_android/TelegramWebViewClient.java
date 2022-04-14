package com.example.vpp_android;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;


public class TelegramWebViewClient extends WebViewClient {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//        if(request.getUrl().toString().contains("token")){
//
//        }
        view.loadUrl(request.getUrl().toString());
        Log.d("@@@", "shouldOverrideUrlLoading: " + request.getUrl().toString());
        return true;
    }
}

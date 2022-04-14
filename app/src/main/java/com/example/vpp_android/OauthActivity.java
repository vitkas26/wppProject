package com.example.vpp_android;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import api_service.APIService;
import api_service.APIUtils;
import auth_classes.TelegramUrl;
import retrofit2.Callback;
import retrofit2.Response;

public class OauthActivity extends AppCompatActivity {
    private EditText phoneNumberEditText;
    private Button sendButton;
    private APIService mApiService;
    private ProgressDialog progressDialog;
    private TextView phoneNumberTextView;
    private WebView telegramWebView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oauth_activity);

        Log.d("@@@", "onCreate: " + savedInstanceState);
        initViews();
        mApiService = APIUtils.getAPIService();
        sendButtonListener();
    }

    private void initViews() {
        phoneNumberEditText = findViewById(R.id.oauth_activity__phone_number_edit_text);
        //put cursor in the end of text
        phoneNumberEditText.setSelection(phoneNumberEditText.getText().length());
        sendButton = findViewById(R.id.oauth_activity__send_button);
        telegramWebView = findViewById(R.id.oauth_activity__telegram_web_view);
        telegramWebView.setVisibility(View.GONE);
        //set listener to textView, when enter key pressed send phone number to server
        phoneNumberEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) || i == EditorInfo.IME_ACTION_DONE)) {
                sendPhone();
            }
            return false;
        });
        phoneNumberTextView = findViewById(R.id.oauth_activity__phone_number_text_view);
        //init ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setTitle("Проверка номера");
        progressDialog.setMessage("Ожидайте пока ваш номер будет идентифицирован");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    private void sendButtonListener() {
        sendButton.setOnClickListener(v -> {
            sendPhone();
        });
    }

    private void sendPhone() {
        if (checkPhoneSize()) {
            Intent intent = new Intent(this, GetSmsActivity.class);
            intent.putExtra(GetSmsActivity.PHONE_NUMBER_TAG, phoneNumberEditText.getText());
            sendPhoneToServer();
            progressDialog.show();
        } else {
            Toast.makeText(this, "Not valid number", Toast.LENGTH_SHORT).show();
        }
    }

    // check if size in phone number is true
    private boolean checkPhoneSize() {
        if (phoneNumberEditText.getText().length() == 12) {
            return true;
        } else {
            return false;
        }

    }

    // make request to server sending phone number
    private void sendPhoneToServer() {
        mApiService.sendTel(phoneNumberEditText.getText().toString()).enqueue(new Callback<TelegramUrl>() {
            @Override
            public void onResponse(Response<TelegramUrl> response) {
                Log.d("@@@", "onResponse: " + response.raw());
                if (response.isSuccess()) {
                    // after success dismiss progressDialog, and go to next activity
                    progressDialog.dismiss();
                    showWebView(response.body().getUrl());
                } else {
                    showMessage(response);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("@@@", "onFailure: " + t.getMessage());
            }
        });
    }

    private void showMessage(Response<TelegramUrl> response) {
        Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void showWebView(String url) {
        phoneNumberEditText.setVisibility(View.GONE);
        phoneNumberTextView.setVisibility(View.GONE);
        sendButton.setVisibility(View.GONE);
        telegramWebView.setVisibility(View.VISIBLE);
        telegramWebView.getSettings().setJavaScriptEnabled(true);
        telegramWebView.getSettings().setLoadWithOverviewMode(true);
        telegramWebView.getSettings().setUseWideViewPort(true);
        telegramWebView.getSettings().setSupportMultipleWindows(true);
        telegramWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        telegramWebView.setWebViewClient(new TelegramWebViewClient());
        telegramWebView.loadUrl(url);

        telegramWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onCloseWindow(WebView window) {
                super.onCloseWindow(window);
                Log.d("@@@", "onCloseWindow: ");
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog,
                                          boolean isUserGesture, Message resultMsg) {


                WebView newWebView = new WebView(getApplicationContext());
                newWebView.getSettings().setJavaScriptEnabled(true);
                newWebView.getSettings().setSupportZoom(true);
                newWebView.getSettings().setBuiltInZoomControls(true);
                newWebView.getSettings().setSupportMultipleWindows(true);
                view.addView(newWebView);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();

                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        Log.d("@@@", "shouldOverrideUrlLoadingCHROME: " + url);
                        return true;
                    }
                });

                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("@@@", "onKeyDown: ");
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (telegramWebView.canGoBack()) {
                telegramWebView.goBack();
            } else {
                finish();
            }
        }
        return true;
    }
}

package com.example.vpp_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import api_service.APIService;
import api_service.APIUtils;
import auth_classes.PhoneNumber;
import retrofit2.Callback;
import retrofit2.Response;

public class OauthActivity extends AppCompatActivity {
    EditText phoneNumberEditText;
    Button sendButton;
    APIService mApiService;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oauth_activity);

        initViews();
        mApiService = APIUtils.getAPIService();
        sendButtonListener();
    }

    private void initViews() {
        phoneNumberEditText = findViewById(R.id.oauth_activity__phone_number_edit_text);
        phoneNumberEditText.setSelection(phoneNumberEditText.getText().length());
        sendButton = findViewById(R.id.oauth_activity__send_button);
    }

    private void sendButtonListener() {
        sendButton.setOnClickListener(v->{
            Intent intent = new Intent(this, GetSmsActivity.class);
            intent.putExtra(GetSmsActivity.PHONE_NUMBER_TAG,phoneNumberEditText.getText());
            sendPhoneToServer(intent);
        });
    }

    private void sendPhoneToServer(Intent intent) {
        startActivity(intent);

        mApiService.sendTel(phoneNumberEditText.getText().toString()).enqueue(new Callback<PhoneNumber>() {
            @Override
            public void onResponse(Response<PhoneNumber> response) {
                if(response.isSuccess()){
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("@@@", "onFailure: " + t.getMessage());
            }
        });
    }
}

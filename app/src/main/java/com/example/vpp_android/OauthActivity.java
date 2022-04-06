package com.example.vpp_android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import api_service.APIService;
import api_service.APIUtils;
import auth_classes.PhoneNumber;
import retrofit2.Callback;
import retrofit2.Response;

public class OauthActivity extends AppCompatActivity {
    private EditText phoneNumberEditText;
    private Button sendButton;
    private APIService mApiService;
    private ProgressDialog progressDialog;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oauth_activity);

        initViews();
        mApiService = APIUtils.getAPIService();
        sendButtonListener();
    }

    private void initViews() {
        phoneNumberEditText = findViewById(R.id.oauth_activity__phone_number_edit_text);
        //put cursor in the end of text
        phoneNumberEditText.setSelection(phoneNumberEditText.getText().length());
        sendButton = findViewById(R.id.oauth_activity__send_button);
        //set listener to textView, when enter key pressed send phone number to server
        phoneNumberEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)|| i == EditorInfo.IME_ACTION_DONE)) {
                sendPhone();
            }
            return false;
        });
        //init ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setTitle("Проверка номера");
        progressDialog.setMessage("Ожидайте пока ваш номер будет идентифицирован");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void sendButtonListener() {
        sendButton.setOnClickListener(v->{
            sendPhone();
        });
    }

    private void sendPhone() {
        if(checkPhoneSize()){
            Intent intent = new Intent(this, GetSmsActivity.class);
            intent.putExtra(GetSmsActivity.PHONE_NUMBER_TAG,phoneNumberEditText.getText());
            sendPhoneToServer(intent);
            progressDialog.show();
        }else {
            Toast.makeText(this, "Not valid number", Toast.LENGTH_SHORT).show();
        }
    }
// check if size in phone number is true
    private boolean checkPhoneSize() {
        if (phoneNumberEditText.getText().length() == 13){
            return true;
        }else{
            return false;
        }
        
    }

    // make request to server sending phone number
    private void sendPhoneToServer(Intent intent) {
        mApiService.sendTel(phoneNumberEditText.getText().toString()).enqueue(new Callback<PhoneNumber>() {
            @Override
            public void onResponse(Response<PhoneNumber> response) {
                Toast.makeText(OauthActivity.this, " " + response.message(), Toast.LENGTH_SHORT).show();
                if(response.isSuccess()){
                    // after success dismiss progressDialog, and go to next activity
                    progressDialog.dismiss();
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("@@@", "onFailure: " + t.getMessage());
            }
        });
    }
}

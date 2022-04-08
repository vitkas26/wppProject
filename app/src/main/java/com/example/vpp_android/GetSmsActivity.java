package com.example.vpp_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import api_service.APIService;
import api_service.APIUtils;
import auth_classes.Token;
import retrofit2.Callback;
import retrofit2.Response;
import savingdata_class.Account;

public class GetSmsActivity extends AppCompatActivity {
    private EditText firstNumberEditText;
    private EditText secondNumberEditText;
    private EditText thirdNumberEditText;
    private EditText fourthNumberEditText;
    private TextView fifthNumberEditText;
    private TextView sixthNumberEditText;
    private Button sendButton;
    private String smsCode;
    private String phoneNumber;
    private APIService mApiService;
    public static final String PHONE_NUMBER_TAG = "PHONE_NUMBER_TAG";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_sms_activity);
        mApiService = APIUtils.getAPIService();
        phoneNumber = getIntent().getStringExtra(PHONE_NUMBER_TAG);
        initViews();
        initListeners();
    }

    private void initViews() {
        firstNumberEditText = findViewById(R.id.get_sms_activity__first_number);
        secondNumberEditText = findViewById(R.id.get_sms_activity__second_number);
        thirdNumberEditText = findViewById(R.id.get_sms_activity__third_number);
        fourthNumberEditText = findViewById(R.id.get_sms_activity__fourth_number);
        fifthNumberEditText = findViewById(R.id.get_sms_activity__fifth_number);
        sixthNumberEditText = findViewById(R.id.get_sms_activity__sixth_number);
        sendButton = findViewById(R.id.get_sms_activity__send_button);
        setCursorPosition();
    }

    // change cursor position in editText after text changed
    private void setCursorPosition() {
        firstNumberEditText.requestFocus();
        firstNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.equals("")) {
                    secondNumberEditText.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        secondNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.equals("")) {
                    thirdNumberEditText.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        thirdNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.equals("")) {
                    fourthNumberEditText.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        fourthNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.equals("")) {
                    fifthNumberEditText.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        fifthNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.equals("")) {
                    sixthNumberEditText.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sixthNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.equals("")) {
                    sendButton.requestFocus();
                    // listen when enter key_code pressed, and send request after it
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // make string with sms from edit texts, check for size
    private boolean generateSmsCode() {
        smsCode = firstNumberEditText.getText() +
                secondNumberEditText.getText().toString() +
                thirdNumberEditText.getText().toString() +
                fourthNumberEditText.getText().toString() +
                fifthNumberEditText.getText().toString() +
                sixthNumberEditText.getText().toString();
        if (smsCode.length() == 6) {
            return true;
        } else {
            showMessage("Не достаточно символов в коде");
            return false;
        }
    }

    private void initListeners() {
        firstNumberEditText.setOnClickListener(v -> {
            if (!firstNumberEditText.getText().toString().equals("")) {
                firstNumberEditText.setText("");
            }
        });
        secondNumberEditText.setOnClickListener(v -> {
            if (!secondNumberEditText.getText().toString().equals("")) {
                secondNumberEditText.setText("");
            }
        });
        thirdNumberEditText.setOnClickListener(v -> {
            if (!thirdNumberEditText.getText().toString().equals("")) {
                thirdNumberEditText.setText("");
            }
        });
        fourthNumberEditText.setOnClickListener(v -> {
            if (!fourthNumberEditText.getText().toString().equals("")) {
                fourthNumberEditText.setText("");
            }
        });

        fifthNumberEditText.setOnClickListener(v -> {
            if (!fifthNumberEditText.getText().toString().equals("")) {
                fifthNumberEditText.setText("");
            }
        });

        sixthNumberEditText.setOnClickListener(v -> {
            if (!sixthNumberEditText.getText().toString().equals("")) {
                sixthNumberEditText.setText("");
            }
        });

        sixthNumberEditText.setOnEditorActionListener((textView, i3, keyEvent) -> {
            if (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER || i3 == EditorInfo.IME_ACTION_DONE) {
                sendSmsRequest();
            }
            return false;
        });

        sendButton.setOnClickListener(v -> sendSmsRequest());
    }

    // send request to server smsCode
    private void sendSmsRequest() {
        if (generateSmsCode()) {
            mApiService.sendSms(smsCode).enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Response<Token> response) {
                    if (response.isSuccess()) {
                        saveSharedPreferences(response.body().getToken());
                        finish();
                    } else {
                        showMessage(response.message());
                        Log.d("@@@", "onResponse: " + response.message());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    showMessage(t.getMessage());
                    Log.d("@@@", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    //if response is successful save token to shared preferences
    private void saveSharedPreferences(String token) {
        SharedPreferences settings = getSharedPreferences(Account.getFILE(), MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Account.getUserToken(), token);
        editor.putBoolean(Account.getInSystem(), true);
        editor.apply();
        Log.d("@@@", "saveSharedPreferences: " + settings.getBoolean(Account.getInSystem(), false));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // show messages in Toast function
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}

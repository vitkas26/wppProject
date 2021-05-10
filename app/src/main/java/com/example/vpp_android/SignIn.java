package com.example.vpp_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import api_service.APIService;
import api_service.APIUtils;
import auth_classes.Authorization;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {
    private static String filePath = "src/main/java/auth_classes/user_id.json";
    EditText login;
    EditText password;
    Button submit;
    APIService mAPIService;
    FileWriter jsonFile;
    JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        login = findViewById(R.id.sign_in_login);
        password = findViewById(R.id.sing_in_password);
        submit = findViewById(R.id.sign_in_btn);

        mAPIService = APIUtils.getAPIService();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authUser(login.getText().toString().trim(), password.getText().toString().trim());
            }
        });
    }

    //Authentication private method
    private void authUser(String username, String password){
            mAPIService.authUser(username, password).enqueue(new Callback<Authorization>(){
                @Override
                public void onResponse(Response<Authorization> response) {
                    if (response.isSuccess()){
                        showMessage("Вход выполнен");
                        int id = response.body().getUserId();
                        String token = String.format("Token %s", response.body().getToken());
                        insertData(id, token);
                        try {
                            writeJson(id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(String.valueOf(id));
                    } else {
                        if (response.code() == 400) {
                            showMessage("Ошибка авторизации. \nПроверьте корректность данных");
                        } else {
                            showMessage("Ошибка");
                        }
                    }
                }
                @Override
                public void onFailure(Throwable t) { }
            });
    }

    private void startActivity(String sessionId){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("EXTRA_SESSION_ID", sessionId);
        startActivity(intent);
    }

    private void writeJson(int userId) throws JSONException {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Insert data to firebase realtime database
    private void insertData(int user_id, String token){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child("User " + user_id).setValue(new Authorization(user_id, token));
    }

    //For show message

    private void showMessage(String text){
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
package com.example.vpp_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    Button signIn;
    FloatingActionButton inputData;
    Button viewData;
    TextView userText;
    private String PREFS_FILE = "Account";
    private String PREF_NAME = "user_id";
    SharedPreferences settings;
    SignIn sign = new SignIn();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_main_activity);

        signIn = findViewById(R.id.sign_in_btn);
        inputData = findViewById(R.id.input_data_btn);
        viewData = findViewById(R.id.view_data);
        userText = findViewById(R.id.sign_user);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String name = sp.getString("user_name", "");

        if (name != ""){
            signIn.setVisibility(View.GONE);
            userText.setText(name);
        }else{
            signIn.setVisibility(View.VISIBLE);
            userText.setVisibility(View.GONE);
        }

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(v, SignIn.class);
            }
        });

        inputData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(v, InputData.class);
            }
        });

        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(v, ViewData.class);
            }
        });


    }

    //callback intent signIn activity
    public void startActivity(View view, Class<? extends Activity> activity){
        try {
            Intent signInIntent = new Intent(getApplicationContext(), activity);
            startActivity(signInIntent);
        } catch (ActivityNotFoundException ex){
            Toast.makeText(this, String.format("%s", ex), Toast.LENGTH_SHORT).show();
        }
    }
}
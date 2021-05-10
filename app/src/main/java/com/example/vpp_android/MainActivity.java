package com.example.vpp_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    Button signIn;
    FloatingActionButton inputData;
    Button viewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_main_activity);

        signIn = findViewById(R.id.sign_in_btn);
        inputData = findViewById(R.id.input_data_btn);
        viewData = findViewById(R.id.view_data);

        Toast.makeText(getBaseContext(), "UserID: " + getIntent()
                .getStringExtra("EXTRA_SESSION_ID"), Toast.LENGTH_SHORT).show();

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
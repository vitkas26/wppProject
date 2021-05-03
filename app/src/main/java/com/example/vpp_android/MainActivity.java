package com.example.vpp_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_main_activity);
    }

    //callback intent signIn activity
    public void signIn(View view){
        try {
            Intent signInIntent = new Intent(getApplicationContext(), SignIn.class);
            startActivity(signInIntent);
        } catch (ActivityNotFoundException ex){
            Toast.makeText(this, String.format("%s", ex), Toast.LENGTH_SHORT).show();
        }
    }
}
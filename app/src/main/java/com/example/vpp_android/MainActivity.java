package com.example.vpp_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    Button signIn;
    FloatingActionButton inputData;
    Button viewData;
    TextView userText;
    BottomAppBar bottomAppBar;
    DrawerLayout drawerLayout;
    private String PREFS_FILE = "Account";
    private String PREF_NAME = "user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_main_activity);
        initAppBar();
        initViews();
        checkLoginVisible();
        setupClickListeners();

    }

    // set click listeners to buttons and appBar
    private void setupClickListeners() {
        signIn.setOnClickListener(v -> startActivity(v, SignIn.class));

        inputData.setOnClickListener(v -> startActivity(v, InputData.class));

        viewData.setOnClickListener(v -> startActivity(v, ViewData.class));

        bottomAppBar.setNavigationOnClickListener(view->{
            BottomSheetDialogMenu bottomSheetDialogMenu = new BottomSheetDialogMenu();
            bottomSheetDialogMenu.show(getSupportFragmentManager(),"BottomSheetDialog");
        });
    }

    // check weather show button or logged in user email
    private void checkLoginVisible() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String name = sp.getString("user_name", "");


        if (name != ""){
            signIn.setVisibility(View.GONE);
            userText.setText(name);
        }else{
            signIn.setVisibility(View.VISIBLE);
            userText.setVisibility(View.GONE);
        }
    }
// initialize Views on Activity
    private void initViews() {
        signIn = findViewById(R.id.sign_in_btn);
        inputData = findViewById(R.id.input_data_btn);
        viewData = findViewById(R.id.view_data);
        userText = findViewById(R.id.sign_user);
        drawerLayout = findViewById(R.id.main_activity_layout);
    }
// initialize appBar
    private void initAppBar() {
        bottomAppBar = findViewById(R.id.bottomBar);
        setSupportActionBar(bottomAppBar);
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
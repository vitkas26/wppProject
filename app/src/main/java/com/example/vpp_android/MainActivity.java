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

    FloatingActionButton inputData;
    Button viewData;
    Button reportsButton;
    BottomAppBar bottomAppBar;
    BottomSheetDialogMenu bottomSheetDialogMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_main_activity);

        bottomSheetDialogMenu = new BottomSheetDialogMenu();

        initAppBar();
        initViews();
        setupClickListeners();

    }

    // set click listeners to buttons and appBar
    private void setupClickListeners() {

        inputData.setOnClickListener(v -> startActivity(v, InputData.class));

        viewData.setOnClickListener(v -> startActivity(v, ViewData.class));

        reportsButton.setOnClickListener(v -> startActivity(v, History.class));

        bottomAppBar.setNavigationOnClickListener(view -> {
            bottomSheetDialogMenu.show(getSupportFragmentManager(), "BottomSheetDialog");
        });


    }


    // initialize Views on Activity
    private void initViews() {
        inputData = findViewById(R.id.input_data_btn);
        viewData = findViewById(R.id.view_data);
        reportsButton = findViewById(R.id.reports_btn);
    }

    // initialize appBar
    private void initAppBar() {
        bottomAppBar = findViewById(R.id.bottomBar);
        setSupportActionBar(bottomAppBar);
    }

    //callback intent to start selected activity
    public void startActivity(View view, Class<? extends Activity> activity) {
        try {
            Intent signInIntent = new Intent(getApplicationContext(), activity);
            startActivity(signInIntent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, String.format("%s", ex), Toast.LENGTH_SHORT).show();
        }
    }
}
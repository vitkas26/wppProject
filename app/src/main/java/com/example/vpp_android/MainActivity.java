package com.example.vpp_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    Button signIn;
    FloatingActionButton inputData;
    Button viewData;
    TextView userText;
    BottomAppBar bottomAppBar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
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
        navigationView = findViewById(R.id.nav_view);
    }
// initialize appBar
    private void initAppBar() {
        bottomAppBar = findViewById(R.id.bottomBar);
        setSupportActionBar(bottomAppBar);
    }

    //inflate menu to view, adding menu to appBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    /* Get Menu Fragment on menu button click
      @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }
        return false;
    }
*/

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
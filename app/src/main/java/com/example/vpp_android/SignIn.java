package com.example.vpp_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Objects;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignIn extends AppCompatActivity {
    EditText login;
    EditText password;
    Button submit;
    APIAuth mAPIAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        login = findViewById(R.id.sign_in_login);
        password = findViewById(R.id.sing_in_password);
        submit = findViewById(R.id.sign_in_btn);

        mAPIAuth = APIUtils.getAPIService();

        String loginText = login.getText().toString();
        String passwordText = password.getText().toString();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authUser(login.getText().toString(), password.getText().toString());
            }
        });
    }

    private void authUser(String username, String password){
        showMessage(password);
            mAPIAuth.authUser(username, password).enqueue(new Callback<Authorization>() {
                @Override
                public void onResponse(Response<Authorization> response) {
                    if (response.isSuccess()) {
                        showMessage("true");
                    }else{
                        showResponse("Code: " + response.code());
                    }
                }
                @Override
                public void onFailure(Throwable t) {

                }
            });
    }

    public void showResponse(String response){
        Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //For show message

    private void showMessage(String text){
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
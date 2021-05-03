package com.example.vpp_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_main_activity);

       Button testRequest = findViewById(R.id.request);

       testRequest.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
               String url ="https://www.google.com";

               StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                       new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {
                               Toast.makeText(MainActivity.this,
                                       String.format("Запрос: %s", response.substring(0,500)),
                                       Toast.LENGTH_SHORT).show();
                           }
                       }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(MainActivity.this, "Что-то пошло не так",
                               Toast.LENGTH_SHORT).show();
                   }
               });

               queue.add(stringRequest);
           }
       });

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
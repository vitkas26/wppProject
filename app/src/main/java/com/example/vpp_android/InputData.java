package com.example.vpp_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InputData extends AppCompatActivity {

    private List<String> items = new ArrayList<>();
    private static final String mainUrl = "http://212.42.106.73/api/v1/";
    Spinner inputDataSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        inputDataSpinner = findViewById(R.id.input_data_spinner);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://212.42.106.73/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        IProduct iProduct = retrofit.create(IProduct.class);

        Call<Product> call = iProduct.getProduct();

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Response<Product> response) {
                if (!response.isSuccess()) {
                    Toast.makeText(getBaseContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Data> itemObj = response.body().getData();
                for (Data obj: itemObj){
                    items.add(obj.getName());
                }
                //Items array adapter in spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, items);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                inputDataSpinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        inputDataSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemPos = String.valueOf(position);
                int posInt = Integer.parseInt(itemPos);
                Toast.makeText(parent.getContext(), "Position: " + posInt, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
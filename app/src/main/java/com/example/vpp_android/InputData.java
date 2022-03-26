package com.example.vpp_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import api_service.APIService;
import api_service.APIUtils;
import api_service.Routes;
import costs_classes.Costs;
import location_service.GpsTracker;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import products_classes.Data;
import products_classes.Product;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InputData extends AppCompatActivity {
    private List<String> items = new ArrayList<>();
    private int costsId;
    Spinner inputDataSpinner;
    APIService mAPIService;
    private GpsTracker gpsTracker;
    int productId;
    Button submitCosts;
    TextInputEditText consumption_rate;
    TextInputEditText produced;
    TextInputEditText stock_by_population;
    TextInputEditText outlet_stock;
    TextInputEditText price;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        mAPIService = APIUtils.getAPIService();

        inputDataSpinner = findViewById(R.id.input_data_spinner);
        submitCosts = findViewById(R.id.submit_costs);
        consumption_rate = findViewById(R.id.consumption_rate);
        produced = findViewById(R.id.produced);
        stock_by_population = findViewById(R.id.stock_by_population);
        outlet_stock = findViewById(R.id.outlet_stock);
        price = findViewById(R.id.price);

        getProduct();
        checkLocationPermission();

        //Listener for post costs data
        submitCosts.setOnClickListener(v -> getLocation(v));

        //Items array adapter in spinner
        inputDataSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemPos = String.valueOf(position);
                costsId = Integer.parseInt(itemPos);
                costsId++;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCosts(int id, String token, float consumption_rate, float produced,
                          float stock_by_population, float outlet_stock,
                          float price, float longitude, float latitude){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String spToken = sp.getString("user_token", "");
        mAPIService.setCost(id, spToken, consumption_rate, produced,
                            stock_by_population,
                            outlet_stock, price,
                            longitude, latitude).enqueue(new Callback<Costs>(){
            @Override
            public void onResponse(Response<Costs> response) {
                if (response.isSuccess()){
                    showMessage("Данные отправлены");
                } else {
                    if (response.code() == 400) {
                        Toast.makeText(getBaseContext(), "400", Toast.LENGTH_SHORT).show();
                    } else {
                        showMessage("Error code: " + response.code());
                    }
                }

            }
            @Override
            public void onFailure(Throwable t) { }
        });
    }

    //Authentication private method
    private void getProduct(){
        mAPIService.getProduct().enqueue(new Callback<Product>(){
            @Override
            public void onResponse(Response<Product> response) {
                if (response.isSuccess()){
                    List<Data> itemObj = response.body().getData();
                    for (Data obj: itemObj){
                        items.add(obj.getName());
                    }
                    addDataToSpinner(items);
                } else {
                    Toast.makeText(getBaseContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Throwable t) { }
        });
    }

    //Check location permission
    private void checkLocationPermission(){
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(InputData.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getLocation(View view){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String spToken = sp.getString("user_token", "");
        gpsTracker = new GpsTracker(InputData.this);

        if(gpsTracker.canGetLocation()){
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            if (latitude == 0 || longitude == 0){
                showMessage("Данные о вашем местополжении не определены\nНажмите на кнопку ещё раз.");
            }else{
                setCosts(costsId, spToken, Float.parseFloat(consumption_rate.getText().toString()), Float.parseFloat(produced.getText().toString()),
                        Float.parseFloat(stock_by_population.getText().toString()), Float.parseFloat(outlet_stock.getText().toString()),
                        Float.parseFloat(price.getText().toString()),
                        (float) longitude, (float) latitude);
            }
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    private void addDataToSpinner(List<String> items){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputDataSpinner.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int getCostsId() {
        return costsId;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void showMessage(String text){
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
    }
}
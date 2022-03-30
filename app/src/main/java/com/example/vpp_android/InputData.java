package com.example.vpp_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import api_service.APIService;
import api_service.APIUtils;
import costs_classes.Costs;
import location_service.GpsTracker;
import products_classes.Data;
import products_classes.DataLoc;
import products_classes.GetCostsLoc;
import products_classes.Product;
import retrofit2.Callback;
import retrofit2.Response;

public class InputData extends AppCompatActivity {
    private List<String> items = new ArrayList<>();
    private List<String> itemsLoc = new ArrayList<>();
    private int costsId, costsIdLoc;
    Spinner inputDataSpinner, inputLocationSpinner;
    APIService mAPIService;
    private GpsTracker gpsTracker;
    Button submitCosts;
    TextInputEditText consumption_rate;
    int location;
    TextInputEditText produced;
    TextInputEditText stock_by_population;
    TextInputEditText outlet_stock;
    TextInputEditText price;
    private String spToken;

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mAPIService = APIUtils.getAPIService();

        inputLocationSpinner = findViewById(R.id.input_location_spinner);
        inputDataSpinner = findViewById(R.id.input_data_spinner);
        submitCosts = findViewById(R.id.submit_costs);
        consumption_rate = findViewById(R.id.consumption_rate);
        produced = findViewById(R.id.produced);
        stock_by_population = findViewById(R.id.stock_by_population);
        outlet_stock = findViewById(R.id.outlet_stock);
        price = findViewById(R.id.price);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("Account", Context.MODE_PRIVATE);
        spToken = sp.getString("user_token", "");


        getProduct();
        checkLocationPermission();
        getLocSpinner();
        //Listener for post costs data
        submitCosts.setOnClickListener(v -> getLocation(v));

        inputLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemPosLoc = String.valueOf(position);
                costsIdLoc = Integer.parseInt(itemPosLoc);
                costsIdLoc++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    private void setCosts(int costsIdLoc,int id, String token, float consumption_rate, float produced,
                          float stock_by_population, float outlet_stock,
                          float price, float longitude, float latitude){
        mAPIService.setCost(costsIdLoc ,id, spToken, consumption_rate, produced,
                            stock_by_population,
                            outlet_stock, price,
                            longitude, latitude).enqueue(new Callback<Costs>(){
            @Override
            public void onResponse(Response<Costs> response) {
                Log.d("location", "onResponse: " + response.raw());
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

    private void getLocSpinner(){
        mAPIService.getLoc(spToken).enqueue(new Callback<GetCostsLoc>(){
            @Override
            public void onResponse(Response<GetCostsLoc> response) {
                if (response.isSuccess()){
                    List<DataLoc> itemLoc = response.body().getData();
                    for (DataLoc obj: itemLoc){
                        itemsLoc.add(obj.getName());
                    }
                    //location = itemLoc.get(0).getId();
                    addDataLocSpinner(itemsLoc);
                } else {
                    Toast.makeText(getBaseContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
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
        gpsTracker = new GpsTracker(InputData.this);
        if(gpsTracker.canGetLocation()){
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            if (latitude == 0 || longitude == 0){
                showMessage("Данные о вашем местополжении не определены\nНажмите на кнопку ещё раз.");
            }else{
                setCosts(costsIdLoc, costsId, spToken, Float.parseFloat(consumption_rate.getText().toString()), Float.parseFloat(produced.getText().toString()),
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

    private void addDataLocSpinner(List<String> itemsLoc){
        ArrayAdapter<String> adapterLoc = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, itemsLoc);
        adapterLoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputLocationSpinner.setAdapter(adapterLoc);
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
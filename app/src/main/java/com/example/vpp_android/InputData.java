package com.example.vpp_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
    EditText consumption_rate;
    EditText produced;
    EditText stock_by_population;
    EditText outlet_stock;
    EditText price;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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
        submitCosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation(v);
                setCosts(costsId,Float.parseFloat(consumption_rate.getText().toString()), Float.parseFloat(produced.getText().toString()),
                        Float.parseFloat(stock_by_population.getText().toString()), Float.parseFloat(outlet_stock.getText().toString()),
                        Float.parseFloat(price.getText().toString()),
                        (float) longitude, (float) latitude);
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

        Button view_report = findViewById(R.id.view_report);
        view_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation(v);
            }
        });
    }

    private void setCosts(int id, float consumption_rate, float produced,
                          float stock_by_population, float outlet_stock,
                          float price, float longitude, float latitude){
        mAPIService.setCost(id,consumption_rate, produced,
                            stock_by_population,
                            outlet_stock, price,
                            longitude, latitude).enqueue(new Callback<Costs>(){
            @Override
            public void onResponse(Response<Costs> response) {
                if (response.isSuccess()){
                    Toast.makeText(getBaseContext(), "Success Code: " + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    if (response.code() == 400) {
                        Toast.makeText(getBaseContext(), "400", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Error Code: " + response.code(), Toast.LENGTH_SHORT).show();
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
        gpsTracker = new GpsTracker(InputData.this);
        if(gpsTracker.canGetLocation()){
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            if (latitude != 0 && longitude != 0){
                Toast.makeText(getBaseContext(), "latitude: " + latitude, Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), "longitude: " + longitude, Toast.LENGTH_SHORT).show();
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
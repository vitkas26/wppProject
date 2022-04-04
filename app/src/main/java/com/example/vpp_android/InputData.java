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

import com.example.vpp_android.districts.DataDistricts;
import com.example.vpp_android.districts.Districts;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import api_service.APIService;
import api_service.APIUtils;
import costs_classes.Costs;
import location_service.GpsTracker;
import products_classes.Data;
import products_classes.Product;
import retrofit2.Callback;
import retrofit2.Response;

public class InputData extends AppCompatActivity {
    private List<String> items = new ArrayList<>();
    private List<String> districts = new ArrayList<>();
    private int costsId;
    private int districtId;
    Spinner inputDataSpinner;
    Spinner inputDistrictSpinner;
    APIService mAPIService;
    private GpsTracker gpsTracker;
    Button submitCosts;
    TextInputEditText consumption_rate;
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

        mAPIService = APIUtils.getAPIService();

        inputDataSpinner = findViewById(R.id.input_data_spinner);
        inputDistrictSpinner = findViewById(R.id.input_data_district_spinner);
        submitCosts = findViewById(R.id.submit_costs);
        consumption_rate = findViewById(R.id.consumption_rate);
        produced = findViewById(R.id.produced);
        stock_by_population = findViewById(R.id.stock_by_population);
        outlet_stock = findViewById(R.id.outlet_stock);
        price = findViewById(R.id.price);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("Account", Context.MODE_PRIVATE);
        spToken = "Token " + sp.getString("user_token", "");

        getDistricts();
        getProduct();
        checkLocationPermission();

        //Listener for post costs data
        submitCosts.setOnClickListener(this::getLocation);

        //Getting product position from spinner to make request by id of product
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
        //Getting district position from spinner to make request by id of region
        inputDistrictSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemPos = String.valueOf(position);
                districtId = Integer.parseInt(itemPos);
                districtId++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // getting data from editTexts and sending them by request setCost
    private void setCosts(int id, int district, String token, float consumption_rate, float produced,
                          float stock_by_population, float outlet_stock,
                          float price, float longitude, float latitude) {
        mAPIService.setCost(id, district, token, consumption_rate, produced,
                stock_by_population,
                outlet_stock, price,
                longitude, latitude).enqueue(new Callback<Costs>() {
            @Override
            public void onResponse(Response<Costs> response) {
                Log.d("@@@", "onResponse: " + response.raw());
                if (response.isSuccess()) {
                    showMessage("Данные отправлены");
                    clearFields();
                } else {
                    if (response.code() == 400) {
                        Toast.makeText(getBaseContext(), "400", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("@@@", "onResponse: " + response.raw());
                        showMessage("Error code: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    // clear fields function used when one product where sent and new product where selected
    private void clearFields() {
        submitCosts.setText("");
        consumption_rate.setText("");
        produced.setText("");
        stock_by_population.setText("");
        outlet_stock.setText("");
        price.setText("");
    }

    //GET request from server getting all products and adding them to spinner
    private void getProduct() {
        mAPIService.getProduct().enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Response<Product> response) {
                if (response.isSuccess()) {
                    List<Data> itemObj = response.body().getData();
                    for (Data obj : itemObj) {
                        items.add(obj.getName());
                    }
                    addDataToSpinner(items);
                } else {
                    Toast.makeText(getBaseContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    // Get request to server getting all available districts and adding them to spinner
    private void getDistricts() {

        mAPIService.getDistricts(spToken).enqueue(new Callback<DataDistricts>() {
            @Override
            public void onResponse(Response<DataDistricts> response) {
                Log.d("@@@", "onResponse: " + response.raw());
                if (response.isSuccess()) {
                    for (Districts item : response.body().getDistricts()
                    ) {
                        districts.add(item.getName());
                    }
                    addDistrictToSpinner(districts);
                } else {
                    Toast.makeText(getBaseContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    //Check location permission
    private void checkLocationPermission() {
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(InputData.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getting phone location and making request to server for adding data
    private void getLocation(View view) {
        gpsTracker = new GpsTracker(InputData.this);
// checking if gpsTracker got users location
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            if (latitude == 0 || longitude == 0) {
                showMessage("Данные о вашем местополжении не определены\nНажмите на кнопку ещё раз.");
            } else {
                //checking for not filled editTexts
                if (consumption_rate.getText().toString().equals("") || produced.getText().toString().equals("") || stock_by_population.getText().toString().equals("") ||
                        outlet_stock.getText().toString().equals("") || price.getText().toString().equals("")) {
                    showMessage("Не все поля были заполнены");
                } else {
                    //sending data for request setCost
                    setCosts(costsId, districtId, spToken, Float.parseFloat(consumption_rate.getText().toString()), Float.parseFloat(produced.getText().toString()),
                            Float.parseFloat(stock_by_population.getText().toString()), Float.parseFloat(outlet_stock.getText().toString()),
                            Float.parseFloat(price.getText().toString()),
                            (float) longitude, (float) latitude);
                }
            }
        } else {
            // if users location where not founded, opens dialog to switch on gps settings
            gpsTracker.showSettingsAlert();
        }
    }
// adding data to product spinner from request getProduct
    private void addDataToSpinner(List<String> items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputDataSpinner.setAdapter(adapter);
    }
// adding data to district spinner from request getDistrict
    private void addDistrictToSpinner(List<String> districts) {
        ArrayAdapter<String> districtsAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, districts);
        districtsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputDistrictSpinner.setAdapter(districtsAdapter);
    }

    private void showMessage(String text) {
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
    }
}
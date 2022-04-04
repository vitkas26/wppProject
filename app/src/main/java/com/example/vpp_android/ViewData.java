package com.example.vpp_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import api_service.APIService;
import api_service.APIUtils;
import costs_classes.Costs;
import costs_classes.CostsData;
import costs_classes.GetCosts;
import costs_classes.MainCostsData;
import products_classes.Data;
import products_classes.Product;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewData extends AppCompatActivity {

    Spinner costsItem;
    private List<String> items = new ArrayList<>();
    Button viewData;
    TextView consumption_rate;
    TextView produced;
    TextView stock_by_population;
    TextView outlet_stock;
    TextView price;
    APIService mAPIService;
    int costsId;
    int locationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        costsItem = findViewById(R.id.costItems);
        viewData = findViewById(R.id.view_data);
        consumption_rate = findViewById(R.id.consumption_rate);
        produced = findViewById(R.id.produced);
        stock_by_population = findViewById(R.id.stock_by_population);
        outlet_stock = findViewById(R.id.outlet_stock);
        price = findViewById(R.id.price);

        mAPIService = APIUtils.getAPIService();

        getProduct();

        //Get product id from position variable in spinner
        costsItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                costsId = ++position;
                locationId = 1;
                getCosts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Get request to server, getting products
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

    // adding product data to spinner
    private void addDataToSpinner(List<String> items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        costsItem.setAdapter(adapter);
    }

    // sending GET request to get products info  by region and product id
    public void getCosts() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String spToken = "Token " + sp.getString("user_token", "");
        Log.d("@@@", "getCosts: " + spToken);

        mAPIService.getCosts(locationId, costsId, spToken).enqueue(new Callback<GetCosts>() {
            @Override
            public void onResponse(Response<GetCosts> response) {
                Log.d("@@@", "onResponse: " + response.raw());
                if (response.isSuccess()) {
                    fillData(response.body().getMainCostsData().getCostsData());

                } else {
                    showMessage("Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    //Fills textViews with data returned from server
    private void fillData(List<CostsData> costsData) {
        Log.d("@@@", "fillData: " + costsData.get(0).getStock_by_population());
        consumption_rate.setText(String.valueOf(costsData.get(0).getConsumption_rate()));
        produced.setText(String.valueOf(costsData.get(0).getProduced()));
        stock_by_population.setText(String.valueOf(costsData.get(0).getStock_by_population()));
        outlet_stock.setText(String.valueOf(costsData.get(0).getOutlet_stock()));
        price.setText(String.valueOf(costsData.get(0).getPrice()));
    }

    private void showMessage(String text) {
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
    }
}
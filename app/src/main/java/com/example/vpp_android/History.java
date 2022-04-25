package com.example.vpp_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.vpp_android.adapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;

import api_service.APIService;
import api_service.APIUtils;
import costs_classes.CostsData;
import costs_classes.GetCosts;
import retrofit2.Callback;
import retrofit2.Response;

public class History extends AppCompatActivity implements HistoryAdapter.ItemClickListener {
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private APIService mApiService;
    private String spToken;
    public List<CostsData> data = new ArrayList<>();
    private ProductItemDialog productItemDialog = new ProductItemDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mApiService = APIUtils.getAPIService();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Account", Context.MODE_PRIVATE);
        spToken = "Token " + sp.getString("user_token", "");
        getData();
    }

    // make request to server to get all histories
    private void getData(){
        int regionId = 1;
        mApiService.getHistory(regionId, spToken).enqueue(new Callback<GetCosts>() {
            @Override
            public void onResponse(Response<GetCosts> response) {
                if (response.isSuccess()) {
                    data.addAll(response.body().getMainCostsData().getCostsData());
                    initRecycler(data);
                    Log.d("@@@", "onResponse: " + response.body().getMainCostsData().getCostsData().size());
                } else {
                    Log.d("@@@", "onResponse: " + response.raw());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                throw new IllegalStateException(t.getMessage());
            }
        });

    }

    // setting up and initialize recycler view
    private void initRecycler(List<CostsData> data) {
        recyclerView = findViewById(R.id.activity_history__recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("@@@", "initRecycler: " + data.size());
        adapter = new HistoryAdapter(this, data);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(this);
    }

    //callback from adapter on item click, when one of histories items where selected
    @Override
    public void onItemClick(View view, CostsData item) {
        Log.d("@@@", "onItemClick: " + item.getStock_by_population());
        ProductItemDialog productItemDialog = new ProductItemDialog();
        productItemDialog.newInstance(item).show(getSupportFragmentManager(), "ProductItemDialog");
    }
}
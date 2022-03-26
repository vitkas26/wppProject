package com.example.vpp_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import api_service.APIService;
import api_service.APIUtils;
import costs_classes.GetCosts;
import costs_classes.MainCostsData;
import retrofit2.Callback;
import retrofit2.Response;
import savingdata_class.Account;

public class BottomSheetDialogMenu extends BottomSheetDialogFragment {
    TextView logout;
    TextView menuAbout;
    TextView menuSettings;
    TextView infoMenu;
    APIService mApiService;

    //Empty constructor to continue working after rotation
    BottomSheetDialogMenu() {
    }

    //Override method, set layout to Fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_fragment, container, false);
    }

    //When Fragment created initialize methods and fields
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //initialize views in layout
        logout = view.findViewById(R.id.logout);
        menuAbout = view.findViewById(R.id.menuAbout);
        menuSettings = view.findViewById(R.id.menuSettings);
        infoMenu = view.findViewById(R.id.infoMenu);
        //setting up API utilities
        mApiService = APIUtils.getAPIService();

        //Getting shared preferences to interact with user account saved in
        SharedPreferences settings = getContext().getSharedPreferences(Account.getFILE(), 0);
        SharedPreferences.Editor editor = settings.edit();
        SharedPreferences sp = getContext().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String name = sp.getString("user_name", "");
        String token = sp.getString("user_token", "");

        //set action to button logout
        logout.setOnClickListener(v -> {

            if (name.equals("")) {
                Toast.makeText(getContext(), "Not logged in", Toast.LENGTH_SHORT).show();
            } else {
                editor.clear();
                editor.commit();
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(view.getContext(), "User logged out", Toast.LENGTH_SHORT).show();
            }
        });

        //set action to menuAbout get user info
        menuAbout.setOnClickListener(v -> {
            //make request to get user data
            mApiService.getCosts(token).enqueue(new Callback<GetCosts>() {
                @Override
                public void onResponse(Response<GetCosts> response) {
                    if (response.isSuccess()) {
                        infoMenu.setText(new StringBuilder(response.body().getMainCostsData().getDistrict())
                                .append("\n")
                                .append(response.body().getMainCostsData().getPhone())
                                .append("\n")
                                .append(response.body().getMainCostsData().getWorker())
                                .append("\n")
                                .append(response.body().getMainCostsData().getPopulation())
                                .append("\n")
                                .append(response.body().getMainCostsData().getRegion())
                        );
                    }
                    Log.d("@@@", "onResponse: " + response.message());
                }
                //if request failed get message to LogCat
                @Override
                public void onFailure(Throwable t) {
                    Log.d("@@@", "onFailure: " + t.getMessage());
                }
            });
        });
    }
}

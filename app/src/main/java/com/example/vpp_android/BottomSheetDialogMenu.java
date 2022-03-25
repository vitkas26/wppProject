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

    BottomSheetDialogMenu() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        logout = view.findViewById(R.id.logout);
        menuAbout = view.findViewById(R.id.menuAbout);
        menuSettings = view.findViewById(R.id.menuSettings);
        infoMenu = view.findViewById(R.id.infoMenu);
        mApiService = APIUtils.getAPIService();

        SharedPreferences settings = getContext().getSharedPreferences(Account.getFILE(), 0);
        SharedPreferences.Editor editor = settings.edit();
        SharedPreferences sp = getContext().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String name = sp.getString("user_name", "");
        String token = sp.getString("user_token", "");

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

        menuAbout.setOnClickListener(v -> {
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

                @Override
                public void onFailure(Throwable t) {
                    Log.d("@@@", "onFailure: " + t.getMessage());
                }
            });

        });
    }
}

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

import api_service.APIService;
import api_service.APIUtils;
import profile.DataProfile;
import retrofit2.Callback;
import retrofit2.Response;
import savingdata_class.Account;

public class BottomSheetDialogMenu extends BottomSheetDialogFragment {
    private TextView logout;
    private TextView menuAbout;
    private TextView menuSettings;
    private TextView infoMenu;
    private APIService mApiService;
    private boolean loginStatus;
    private SharedPreferences settings;


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
        settings = getContext().getSharedPreferences(Account.getFILE(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        SharedPreferences sp = getContext().getSharedPreferences(Account.getFILE(), Context.MODE_PRIVATE);

        checkLoginStatus();


        //set action to button logout
        logout.setOnClickListener(v -> {
            String token = sp.getString(Account.getUserToken(), "not exist");
            if (token.equals("not exist")) {
                Intent intent = new Intent(getContext(), OauthActivity.class);
                startActivity(intent);
                requireActivity().finish();
            } else {
                editor.clear();
                editor.apply();
                infoMenu.setText("");
                checkLoginStatus();
                Toast.makeText(view.getContext(), "User logged out", Toast.LENGTH_SHORT).show();
            }
        });

        //set action to menuAbout get user info
        menuAbout.setOnClickListener(v -> {
            //make request to get user data
            String token = sp.getString(Account.getUserToken(), "");
            String tokenAddHeader = "Token " + token;
            mApiService.getEmployee(tokenAddHeader).enqueue(new Callback<DataProfile>() {
                @Override
                public void onResponse(Response<DataProfile> response) {
                    if (response.isSuccess()) {
                        infoMenu.setText(new StringBuilder(response.body().getProfile().getFirstName())
                                .append("\n")
                                .append(response.body().getProfile().getLastName())
                                .append("\n")
                                .append(response.body().getProfile().getMiddleName())
                                .append("\n")
                                .append(response.body().getProfile().getPhone()));
                    }
                    Toast.makeText(getContext(), " " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.d("@@@", "onResponse: " + response.message());
                }

                //if request failed get message to LogCat
                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getContext(), " " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("@@@", "onFailure: " + t.getMessage());
                }
            });
        });
    }

    private void checkLoginStatus() {
        loginStatus = settings.getBoolean(Account.getInSystem(), false);
        if (loginStatus) {
            logout.setText("выйти");
        } else {
            logout.setText("войти");
        }
        Log.d("@@@", "checkLoginStatus: " + settings.getBoolean(Account.getInSystem(), false));
    }

}

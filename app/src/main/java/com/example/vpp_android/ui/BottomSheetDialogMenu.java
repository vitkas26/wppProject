package com.example.vpp_android.ui;

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

import com.example.vpp_android.ui.activities.OauthActivity;
import com.example.vpp_android.R;
import com.example.vpp_android.domain.UserLocation;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.example.vpp_android.utils.api_service.APIService;
import com.example.vpp_android.utils.api_service.APIUtils;
import com.example.vpp_android.utils.location_service.GpsTracker;
import com.example.vpp_android.domain.profile.DataProfile;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.vpp_android.domain.savingdata_class.Account;

public class BottomSheetDialogMenu extends BottomSheetDialogFragment {
    private TextView logout;
    private TextView menuAbout;
    private TextView menuSettings;
    private TextView infoMenu;
    private APIService mApiService;
    private boolean loginStatus;
    private int locationId;
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
            int location = getLocation();
            mApiService.getEmployee(location, tokenAddHeader).enqueue(new Callback<DataProfile>() {
                @Override
                public void onResponse(Response<DataProfile> response) {
                    Log.d("@@@", "onResponse: " + response.body());
                    if (response.isSuccess()) {
                        infoMenu.setText(new StringBuilder(response.body().getProfile().getRegion())
                                .append("\n")
                                .append(response.body().getProfile().getDistrict())
                                .append("\n")
                                .append(response.body().getProfile().getWorker())
                                .append("\n")
                                .append(response.body().getProfile().getPhone())
                                .append("\n")
                                .append(response.body().getProfile().getPopulation()));
                    }
                    Log.d("@@@", "onResponse: " + response.message());
                    try {
                        Toast.makeText(getContext(), " " + response.message(), Toast.LENGTH_SHORT).show();
                    } catch (Exception exception) {
                        Log.d("@@@", "Not found activity: " + exception.getMessage());
                    }
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

    private int getLocation() {
        GpsTracker gpsTracker = new GpsTracker(getContext());
        float latitude;
        float longitude;
// checking if gpsTracker got users location
        if (gpsTracker.canGetLocation()) {
            latitude = (float) gpsTracker.getLatitude();
            longitude = (float) gpsTracker.getLongitude();
            if (latitude == 0 || longitude == 0) {
                showMessage("Данные о вашем местополжении не определены\nНажмите на кнопку ещё раз.");
            } else {
                SharedPreferences sp = getContext().getSharedPreferences(Account.getFILE(), Context.MODE_PRIVATE);
                String token = sp.getString(Account.getUserToken(), "");
                String tokenAddHeader = "Token " + token;
                mApiService.postUserLocation(tokenAddHeader,longitude, latitude).enqueue(new Callback<UserLocation>() {
                    @Override
                    public void onResponse(Response<UserLocation> response) {
                        Log.d("@@@", "BottomSheet getLocation() onResponse: " + response.raw());
                        if (response.isSuccess()) {
                            locationId = response.body().getLocation();
                        }
                        Log.d("@@@", "onResponse: " + response.message());
                        try {
                            Toast.makeText(getContext(), " " + response.message(), Toast.LENGTH_SHORT).show();
                        } catch (Exception exception) {
                            Log.d("@@@", "Not found activity: " + exception.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        } else {
            // if users location where not founded, opens dialog to switch on gps settings
            gpsTracker.showSettingsAlert();
        }
        return locationId;
    }

    private void showMessage(String toast) {
        Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
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

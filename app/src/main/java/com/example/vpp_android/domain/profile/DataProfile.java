package com.example.vpp_android.domain.profile;

import com.google.gson.annotations.SerializedName;

public class DataProfile {
    @SerializedName("data")
    Profile profile;

    public Profile getProfile() {
        return profile;
    }
}

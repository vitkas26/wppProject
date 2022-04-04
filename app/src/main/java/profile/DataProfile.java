package profile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataProfile {
    @SerializedName("data")
    Profile profile;

    public Profile getProfile() {
        return profile;
    }
}

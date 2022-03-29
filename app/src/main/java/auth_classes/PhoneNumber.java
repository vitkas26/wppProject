package auth_classes;

import com.google.gson.annotations.SerializedName;

public class PhoneNumber {
    @SerializedName("phone_number")
    private String tel;

    public String getTel() {
        return tel;
    }
}

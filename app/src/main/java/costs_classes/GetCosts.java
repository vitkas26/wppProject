package costs_classes;

import com.google.gson.annotations.SerializedName;

public class GetCosts {
    @SerializedName("data")
    MainCostsData mainCostsData;

    public MainCostsData getMainCostsData() {
        return mainCostsData;
    }
}

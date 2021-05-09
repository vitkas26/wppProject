package costs_classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainCostsData {
    String region;
    String district;
    int population;
    String worker;
    @SerializedName("costs")
    private List<CostsData> costsData;

    public String getRegion() {
        return region;
    }

    public String getDistrict() {
        return district;
    }

    public int getPopulation() {
        return population;
    }

    public String getWorker() {
        return worker;
    }

    public List<CostsData> getCostsData() {
        return costsData;
    }
}

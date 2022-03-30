package products_classes;

import com.google.gson.annotations.SerializedName;

public class DataLoc {
    @SerializedName("id")
    private int id;

    @SerializedName("region")
    private String region;

    @SerializedName("name")
    private String name;

    @SerializedName("population")
    private String population;

    @SerializedName("create_at")
    private int create_at;

    public int getId() {
        return id;
    }

    public String getRegion() {
        return region;
    }

    public String getName() {
        return name;
    }

    public String getPopulation() {
        return population;
    }

    public int getCreate_at() {
        return create_at;
    }
}

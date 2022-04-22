package profile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import costs_classes.CostsData;

public class Profile {
    //profile request
//    @SerializedName("id")
//    private Integer id;
//    @SerializedName("first_name")
//    private String firstName;
//    @SerializedName("last_name")
//    private String lastName;
//    @SerializedName("middle_name")
//    private String middleName;
//    @SerializedName("phone")
//    private String phone;
//    @SerializedName("created_at")
//    private String createdAt;
//    @SerializedName("updated_at")
//    private String updatedAt;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public String getMiddleName() {
//        return middleName;
//    }
//
//    public String getPhone() {
//        return phone;
//    }

// Temporarily got from MainCostData
    String region;
    String district;
    int population;
    String worker;
    String phone;
//    @SerializedName("costs")
//    private List<CostsData> costsData;

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

    public String getPhone() {
        return phone;
    }

//    public List<CostsData> getCostsData() {
//        return costsData;
//    }

}

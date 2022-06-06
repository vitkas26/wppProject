package com.example.vpp_android.domain.products_classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class QuestionnaireDataNew implements Parcelable {
    @SerializedName("longitude")
    Float longitude;
    @SerializedName("latitude")
    Float latitude;
    @SerializedName("producer_type")
    int producerType;
    @SerializedName("enterprise_status")
    int enterpriseStatus;
    @SerializedName("is_active")
    int isActive;
    @SerializedName("manager_name")
    String managerName;

    @SerializedName("phone")
    Double phone;

    @SerializedName("products")
    List<QuestionnaireProduct> products = new ArrayList<>();
    public QuestionnaireDataNew() {
    }

    protected QuestionnaireDataNew(Parcel in) {
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readFloat();
        }
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readFloat();
        }
        managerName = in.readString();
        phone = in.readDouble();
        producerType = in.readInt();
        enterpriseStatus = in.readInt();
        isActive = in.readInt();
    }

    public static final Creator<QuestionnaireDataNew> CREATOR = new Creator<QuestionnaireDataNew>() {
        @Override
        public QuestionnaireDataNew createFromParcel(Parcel in) {
            return new QuestionnaireDataNew(in);
        }

        @Override
        public QuestionnaireDataNew[] newArray(int size) {
            return new QuestionnaireDataNew[size];
        }
    };

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Double getPhone() {
        return phone;
    }

    public void setPhone(Double phone) {
        this.phone = phone;
    }

    public int getProducerType() {
        return producerType;
    }

    public void setProducerType(int producerType) {
        this.producerType = producerType;
    }

    public int getEnterpriseStatus() {
        return enterpriseStatus;
    }

    public void setEnterpriseStatus(int enterpriseStatus) {
        this.enterpriseStatus = enterpriseStatus;
    }

    public int getActive() {
        return isActive;
    }

    public void setActive(int active) {
        this.isActive = active;
    }


    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public List<QuestionnaireProduct> getProducts() {
        return products;
    }

    public void setProducts(List<QuestionnaireProduct> products) {
        this.products = products;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (longitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(longitude);
        }
        if (latitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(latitude);
        }
        parcel.writeInt(producerType);
        parcel.writeInt(enterpriseStatus);
        parcel.writeDouble(phone);
        parcel.writeString(managerName);
        parcel.writeInt(isActive);
    }
}

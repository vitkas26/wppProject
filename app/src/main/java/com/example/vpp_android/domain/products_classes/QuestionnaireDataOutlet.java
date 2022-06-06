package com.example.vpp_android.domain.products_classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class QuestionnaireDataOutlet implements Parcelable {
    @SerializedName("longitude")
    Float longitude;
    @SerializedName("latitude")
    Float latitude;
    @SerializedName("producer_type")
    int implementationType;
    @SerializedName("enterprise_status")
    int outletStatus;
    @SerializedName("is_active")
    int isActive;
    @SerializedName("products")
    List<QuestionnaireProductOutlet> products = new ArrayList<>();

    public QuestionnaireDataOutlet() {
    }

    protected QuestionnaireDataOutlet(Parcel in) {
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
        implementationType = in.readInt();
        outletStatus = in.readInt();
        isActive = in.readInt();
    }

    public static final Creator<QuestionnaireDataOutlet> CREATOR = new Creator<QuestionnaireDataOutlet>() {
        @Override
        public QuestionnaireDataOutlet createFromParcel(Parcel in) {
            return new QuestionnaireDataOutlet(in);
        }

        @Override
        public QuestionnaireDataOutlet[] newArray(int size) {
            return new QuestionnaireDataOutlet[size];
        }
    };

    public int getImplementationType() {
        return implementationType;
    }

    public void setImplementationType(int implementationType) {
        this.implementationType = implementationType;
    }

    public int getOutletStatus() {
        return outletStatus;
    }

    public void setOutletStatus(int outletStatus) {
        this.outletStatus = outletStatus;
    }

    public int getActive() {
        return isActive;
    }

    public void setActive(int active) {
        isActive = active;
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

    public List<QuestionnaireProductOutlet> getProducts() {
        return products;
    }

    public void setProducts(List<QuestionnaireProductOutlet> products) {
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
        parcel.writeInt(implementationType);
        parcel.writeInt(outletStatus);
        parcel.writeInt(isActive);
    }
}

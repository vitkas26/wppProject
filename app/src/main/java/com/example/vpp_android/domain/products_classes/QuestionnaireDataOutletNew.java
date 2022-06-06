package com.example.vpp_android.domain.products_classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class QuestionnaireDataOutletNew implements Parcelable {
    @SerializedName("longitude")
    Float longitude;
    @SerializedName("latitude")
    Float latitude;
    @SerializedName("implementation_type")
    int implementationType;
    @SerializedName("outlet_status")
    int outletStatus;
    @SerializedName("is_active")
    int isActive;
    @SerializedName("manager_name")
    String managerName;
    @SerializedName("phone_number")
    Double phone;

    @SerializedName("products")
    List<QuestionnaireProductOutlet> products = new ArrayList<>();

    public QuestionnaireDataOutletNew() {
    }

    protected QuestionnaireDataOutletNew(Parcel in) {
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
        managerName = in.readString();
        isActive = in.readInt();
        if (in.readByte() == 0) {
            phone = null;
        } else {
            phone = in.readDouble();
        }
    }

    public static final Creator<QuestionnaireDataOutletNew> CREATOR = new Creator<QuestionnaireDataOutletNew>() {
        @Override
        public QuestionnaireDataOutletNew createFromParcel(Parcel in) {
            return new QuestionnaireDataOutletNew(in);
        }

        @Override
        public QuestionnaireDataOutletNew[] newArray(int size) {
            return new QuestionnaireDataOutletNew[size];
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
        parcel.writeString(managerName);
        if (phone == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(phone);
        }
    }
}

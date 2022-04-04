package costs_classes;

import android.os.Parcel;
import android.os.Parcelable;

public class CostsData implements Parcelable {
    float consumption_rate;
    float area_consumption;
    float produced;
    float stock_by_population;
    float outlet_stock;
    float residual_volume;
    float price;
    float availability_in_months;
    String createdAt;

    protected CostsData(Parcel in) {
        consumption_rate = in.readFloat();
        area_consumption = in.readFloat();
        produced = in.readFloat();
        stock_by_population = in.readFloat();
        outlet_stock = in.readFloat();
        residual_volume = in.readFloat();
        price = in.readFloat();
        availability_in_months = in.readFloat();
        createdAt = in.readString();
    }

    public static final Creator<CostsData> CREATOR = new Creator<CostsData>() {
        @Override
        public CostsData createFromParcel(Parcel in) {
            return new CostsData(in);
        }

        @Override
        public CostsData[] newArray(int size) {
            return new CostsData[size];
        }
    };

    public float getConsumption_rate() {
        return consumption_rate;
    }

    public float getArea_consumption() {
        return area_consumption;
    }

    public float getProduced() {
        return produced;
    }

    public float getStock_by_population() {
        return stock_by_population;
    }

    public float getOutlet_stock() {
        return outlet_stock;
    }

    public float getResidual_volume() {
        return residual_volume;
    }

    public float getPrice() {
        return price;
    }

    public float getAvailability_in_months() {
        return availability_in_months;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(consumption_rate);
        parcel.writeFloat(area_consumption);
        parcel.writeFloat(produced);
        parcel.writeFloat(stock_by_population);
        parcel.writeFloat(outlet_stock);
        parcel.writeFloat(residual_volume);
        parcel.writeFloat(price);
        parcel.writeFloat(availability_in_months);
        parcel.writeString(createdAt);
    }
}

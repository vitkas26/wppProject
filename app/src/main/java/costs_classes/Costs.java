package costs_classes;

import com.google.gson.annotations.SerializedName;

public class Costs {
    private float consumption_rate;
    private float produced;
    private float stock_by_population;
    private float outlet_stock;
    private float price;
    private float longitude;
    private float latitude;
    private int id;


    public float getConsumption_rate() {
        return consumption_rate;
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

    public float getPrice() {
        return price;
    }
}

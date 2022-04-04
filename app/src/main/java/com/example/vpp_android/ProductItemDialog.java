package com.example.vpp_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import costs_classes.CostsData;

public class ProductItemDialog extends BottomSheetDialogFragment {
    private static final String COSTS_DATA_ENTITY = "COSTS_DATA_ENTITY";
    private TextView nameOfProduct;
    private TextView consumption_rate;
    private TextView areaConsumptionRate;
    private TextView produced;
    private TextView stockByPopulation;
    private TextView outletStock;
    private TextView residualVolume;
    private TextView price;
    private TextView availabilityInMonth;
    private TextView createdAt;
    private CostsData costsData;

    public static ProductItemDialog newInstance(CostsData costsData){
        ProductItemDialog productItemDialog = new ProductItemDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(COSTS_DATA_ENTITY, costsData);
        productItemDialog.setArguments(bundle);
        return productItemDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_item_dialog, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        costsData = getArguments().getParcelable(COSTS_DATA_ENTITY);
        fillTextViews();
    }

    private void initViews(View view) {
        nameOfProduct = view.findViewById(R.id.product_item_dialog__name_of_product);
        consumption_rate = view.findViewById(R.id.product_item_dialog__consumption_rate);
        areaConsumptionRate = view.findViewById(R.id.product_item_dialog__area_consumption);
        produced = view.findViewById(R.id.product_item_dialog__produced);
        stockByPopulation = view.findViewById(R.id.product_item_dialog__stock_by_population);
        outletStock = view.findViewById(R.id.product_item_dialog__outlet_stock);
        residualVolume = view.findViewById(R.id.product_item_dialog__residual_volume);
        price = view.findViewById(R.id.product_item_dialog__price);
        availabilityInMonth = view.findViewById(R.id.product_item_dialog__availability_in_month);
        createdAt = view.findViewById(R.id.product_item_dialog__created_at);
    }

    private void fillTextViews() {
        consumption_rate.setText(Float.toString(costsData.getConsumption_rate()));
        areaConsumptionRate.setText(Float.toString(costsData.getArea_consumption()));
        produced.setText(Float.toString(costsData.getProduced()));
        stockByPopulation.setText(Float.toString(costsData.getStock_by_population()));
        outletStock.setText(Float.toString(costsData.getOutlet_stock()));
        residualVolume.setText(Float.toString(costsData.getResidual_volume()));
        price.setText(Float.toString(costsData.getPrice()));
        availabilityInMonth.setText(Float.toString(costsData.getAvailability_in_months()));
        createdAt.setText(costsData.getCreatedAt());
    }
}

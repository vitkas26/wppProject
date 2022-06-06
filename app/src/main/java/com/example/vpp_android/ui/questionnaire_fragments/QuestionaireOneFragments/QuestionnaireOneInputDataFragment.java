package com.example.vpp_android.ui.questionnaire_fragments.QuestionaireOneFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.vpp_android.R;
import com.example.vpp_android.domain.products_classes.Data;
import com.example.vpp_android.domain.products_classes.Product;
import com.example.vpp_android.domain.products_classes.QuestionnaireDataNew;
import com.example.vpp_android.domain.products_classes.QuestionnaireProduct;
import com.example.vpp_android.domain.savingdata_class.Account;
import com.example.vpp_android.ui.questionnaire_fragments.QuestionnaireListener;
import com.example.vpp_android.utils.api_service.APIService;
import com.example.vpp_android.utils.api_service.APIUtils;
import com.example.vpp_android.utils.location_service.GpsTracker;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.IllegalFormatFlagsException;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class QuestionnaireOneInputDataFragment extends Fragment {

    public final static String BUNDLE_KEY = "BUNDLE_KEY";
    private List<QuestionnaireProduct> productsToSendList = new ArrayList<>();
    private List<String> productsInSpinner = new ArrayList<>();
    private List<Data> responseProduct = new ArrayList<>();
    private List<Data> responseProductParents = new ArrayList<>();
    private int productsPositionInSpinner;
    private Button addProductButton;
    private Button sendButton;
    private Spinner productSpinner;
    private Spinner productSpinnerSpecials;
    private TextView productId;
    private TextView productUnit;
    private TextView addedProducts;
    private APIService mAPIService;
    private EditText surplusEditText;
    private EditText producedEditText;
    private EditText shippedEditText;
    private EditText remainEditText;
    public Double latitude;
    public Double longitude;
    private QuestionnaireDataNew questionnaireDataNew;
    private QuestionnaireListener questionnaireListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.questionnaireListener = (QuestionnaireListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.questionnaire_one_input_data_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questionnaireDataNew = getArguments().getParcelable(BUNDLE_KEY);
        mAPIService = APIUtils.getAPIService();
        getProduct();
        initViews(view);

    }

    private void getProduct() {
        mAPIService.getProduct().enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Response<Product> response) {
                if (response.isSuccess()) {
                    responseProduct = response.body().getData();
                    for (Data obj : responseProduct) {
                        productsInSpinner.add(obj.getName());
                    }
                    addDataToSpinner(productsInSpinner);
                } else {
                    Toast.makeText(requireContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void addDataToSpinner(List<String> productsSpinner) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, productsSpinner);
        productSpinner.setAdapter(spinnerAdapter);
    }

    private void addDataToSpinnerParents(List<String> productParents) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, productParents);
        productSpinnerSpecials.setAdapter(spinnerAdapter);
    }

    private void initViews(View view) {
        addProductButton = view.findViewById(R.id.questionnaire_input_data_fragment__add_product_button);
        sendButton = view.findViewById(R.id.questionnaire_input_data_fragment__button);
        productSpinner = view.findViewById(R.id.questionnaire_input_data_fragment__spinner);
        productSpinnerSpecials = view.findViewById(R.id.questionnaire_input_data_fragment__spinner_specials);
        productId = view.findViewById(R.id.questionnaire_input_data_fragment__product_id_text_view);
        productUnit = view.findViewById(R.id.questionnaire_input_data_fragment__unit);
        addedProducts = view.findViewById(R.id.questionnaire_input_data_fragment__addedProducts_textView);
        surplusEditText = view.findViewById(R.id.questionnaire_input_data_fragment__remainder);
        producedEditText = view.findViewById(R.id.questionnaire_input_data_fragment__production);
        shippedEditText = view.findViewById(R.id.questionnaire_input_data_fragment__shipped);
        remainEditText = view.findViewById(R.id.questionnaire_input_data_fragment__remainder1);
        setClickListeners();
    }

    private void setClickListeners() {
        shippedEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    remainEditText.setText(String.valueOf(Float.parseFloat(surplusEditText.getText().toString())
                            + Float.parseFloat(producedEditText.getText().toString())
                            - Float.parseFloat(shippedEditText.getText().toString())));
                } catch (Exception e) {
                    showMessage("no data");
                }
            }
        });

        addProductButton.setOnClickListener(v -> {
            boolean productExist = false;
            QuestionnaireProduct product = collectData();
            if (!productsToSendList.isEmpty()) {
                for (int i = 0; i < productsToSendList.size(); i++) {
                    if (productsToSendList.get(i).getId() == product.getId()) {
                        showMessage("Продукт уже добавлен");
                        productExist = true;
                    }
                }
                if (!productExist) {
                    addProductToList(product);
                }
            } else {
                addProductToList(product);
            }
        });

        sendButton.setOnClickListener(v -> getLocation());

        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                productId.setText(String.valueOf(responseProduct.get(i).getId()));
                productUnit.setText(responseProduct.get(i).getUnit());
                productsPositionInSpinner = i;
                getProductParents(responseProduct.get(i).getCode());
            }

            private void getProductParents(String code) {
                List<String> productsParentInSpinner = new ArrayList<>();
                mAPIService.getProductsParent(code).enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Response<Product> response) {
                        Log.d("@@@", "getProductsParent: " + response.raw() + " " + code);
                        if (response.isSuccess()) {
                            responseProductParents = response.body().getData();
                            if (responseProductParents.isEmpty()) {
                                productsParentInSpinner.add("Список пуст");
                            } else {
                                for (Data obj : responseProductParents) {
                                    productsParentInSpinner.add(obj.getName());
                                }
                            }
                            addDataToSpinnerParents(productsParentInSpinner);
                        } else {
                            Toast.makeText(requireContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addProductToList(QuestionnaireProduct product) {
        productsToSendList.add(product);
        showMessage("Продукт добавлен в список");
        showAddedProduct(responseProduct.get(productsPositionInSpinner).getName());
    }

    private void postData(QuestionnaireDataNew postData) {
        SharedPreferences spToken = requireActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String token = "Token " + spToken.getString(Account.getUserToken(), "");
        Gson gson = new Gson();
        String json = gson.toJson(postData);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("@@@", "postData: " + json + token);
        mAPIService.postQuestionnaireOneNew(token, postData).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Response<JSONObject> response) {
                Log.d("@@@", "onResponse: " + response.raw());
                if (response.isSuccess()) {
                    showMessage("Данные отправлены");
                    openActivity();
                } else {
                    Log.d("@@@", "On Error " + response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                showMessage("Error code: " + t.getMessage());
            }
        });

    }

    private void openActivity() {
        questionnaireListener.chooseQuestionnaire(0);
    }

    private void getLocation() {
        GpsTracker gpsTracker = new GpsTracker(getContext());
// checking if gpsTracker got users location
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            if (latitude == 0 || longitude == 0) {
                showMessage("Данные о вашем местополжении не определены\nНажмите на кнопку ещё раз.");
            } else {
                //checking for not filled editTexts
                if (surplusEditText.getText().toString().equals("") || shippedEditText.getText().toString().equals("") || remainEditText.getText().toString().equals("")) {
                    showMessage("Не все поля были заполнены");
                } else {
                    questionnaireDataNew.getProducts().addAll(productsToSendList);
                    questionnaireDataNew.setLatitude(latitude.floatValue());
                    questionnaireDataNew.setLongitude(longitude.floatValue());
                    postData(questionnaireDataNew);
                }
            }
        } else {
            // if users location where not founded, opens dialog to switch on gps settings
            gpsTracker.showSettingsAlert();
        }
    }


    private void showMessage(String toast) {
        Toast.makeText(requireContext(), toast, Toast.LENGTH_SHORT).show();
    }

    private void showAddedProduct(String product) {
        addedProducts.setText(new StringBuilder()
                .append(addedProducts.getText().toString()).append("\n")
                .append(product)
                .append("\n")
        );
    }

    @NonNull
    private QuestionnaireProduct collectData() {
        QuestionnaireProduct product = new QuestionnaireProduct();
        if (productId.getText().toString().isEmpty()
                || productId.getText().toString().isEmpty()
                || producedEditText.getText().toString().isEmpty()
                || shippedEditText.getText().toString().isEmpty()) {
            showMessage("Введите данные о продукте");
        } else {
            product.setId(Integer.parseInt(productId.getText().toString()));
            product.setSurplus(Float.parseFloat(surplusEditText.getText().toString()));
            product.setProduction(Float.parseFloat(producedEditText.getText().toString()));
            product.setShipped(Float.parseFloat(shippedEditText.getText().toString()));
        }
        return product;
    }

}

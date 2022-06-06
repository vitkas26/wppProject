package com.example.vpp_android.ui.questionnaire_fragments.QuestionaireOneFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.vpp_android.ui.questionnaire_fragments.QuestionnaireListener;
import com.example.vpp_android.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionnaireOneThirdFragment extends Fragment {

    private QuestionnaireListener questionnaireListener;
    private Button questionnaire_one_third_fragment_button;
    private int enterpriseStatus;
    private int producerType;
    private int isActive = 0;
    private int poleId = 1;
    private RadioGroup enterpriseStatusRadioGroup;
    private Spinner producerTypeSpinner;
    private RadioButton activeRadioButton;
    private RadioButton temporaryRadioButton;
    private RadioButton seasonRadioButton;
    private RadioButton regularRadioButton;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.questionnaireListener = (QuestionnaireListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.questionnaire_one_third_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initSpinner();
    }

    private void initSpinner() {
        List<String> adapterList = generateSpinnerList();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, adapterList);
        producerTypeSpinner.setAdapter(spinnerAdapter);
        producerTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        producerType = 1;
                        break;
                    case 1:
                        producerType = 2;
                        break;
                    case 2:
                        producerType = 3;
                        break;
                    default:
                        producerType = -1;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @NonNull
    private List<String> generateSpinnerList() {
        List<String> adapterList = new ArrayList<>();
        adapterList.add(getString(R.string.quest_one_third_frag_enterp_stat1));
        adapterList.add(getString(R.string.quest_one_third_frag_enterp_stat2));
        adapterList.add(getString(R.string.quest_one_third_frag_enterp_stat3));
        return adapterList;
    }

    private void initViews(View view) {
        enterpriseStatusRadioGroup = view.findViewById(R.id.questionnaire_one_third_fragment__enterprise_status_radio_group);
        activeRadioButton = view.findViewById(R.id.questionnaire_one_third_fragment__active_radio_button);
        temporaryRadioButton = view.findViewById(R.id.questionnaire_one_third_fragment__temp_radio_button);
        seasonRadioButton = view.findViewById(R.id.questionnaire_one_third_fragment__season_radio_button);
        regularRadioButton = view.findViewById(R.id.questionnaire_one_third_fragment__regular_radio_button);
        questionnaire_one_third_fragment_button = view.findViewById(R.id.questionnaire_one_third_fragment_button);
        producerTypeSpinner = view.findViewById(R.id.producer_type_spinner);
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        questionnaire_one_third_fragment_button.setOnClickListener(v -> {
            questionnaireListener.sendDataThirdFragment(producerType, enterpriseStatus, isActive, poleId);
            questionnaireListener.chooseQuestionnaire(13);
        });
        activeRadioButton.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                isActive = 1;
            } else isActive = 2;
        });
        enterpriseStatusRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.questionnaire_one_third_fragment__regular_radio_button:
                    enterpriseStatus = 4;
                    break;
                case R.id.questionnaire_one_third_fragment__season_radio_button:
                    enterpriseStatus = 3;
                    break;
                case R.id.questionnaire_one_third_fragment__temp_radio_button:
                    enterpriseStatus = 2;
                    break;
                case R.id.questionnaire_one_third_fragment__active_radio_button:
                    enterpriseStatus = 1;
                    break;
                default:
                    enterpriseStatus = 0;
                    break;
            }
        });
    }
}

package com.example.vpp_android.ui.questionnaire_fragments.QuestionnaireSecondFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.vpp_android.ui.questionnaire_fragments.QuestionnaireListener;
import com.example.vpp_android.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class QuestionnaireTwoThirdFragment extends Fragment {

    private QuestionnaireListener questionnaireListener;
    private AutoCompleteTextView dropList;
    private Button questionnaire_second_third_fragment_button;
    private RadioGroup radioGroup;
    private RadioGroup shopRadioGroup;
    private TextInputLayout outletContainersLayout;
    private TextInputLayout containersCountLayout;
    private int enterpriseStatus;
    private int producerType;
    private int isActive = 0;
    private int poleId = 2;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.questionnaireListener = (QuestionnaireListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.questionnaire_second_third_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setListeners();
    }

    private List<String> generateTextDropList() {
        List<String> dropListText = new ArrayList<>();
        dropListText.add(getString(R.string.quest_two_third_frag_outlet_stat1));
        dropListText.add(getString(R.string.quest_two_third_frag_outlet_stat2));
        dropListText.add(getString(R.string.quest_two_third_frag_outlet_stat3));
        dropListText.add(getString(R.string.quest_two_third_frag_outlet_stat4));
        return dropListText;
    }

    private void setListeners() {
        questionnaire_second_third_fragment_button.setOnClickListener(v -> {
            questionnaireListener.chooseQuestionnaire(23);
//            questionnaireListener.sendDataThirdFragment();
        });
        dropList.setOnItemClickListener((adapterView, view, i, l) -> {
            setVisibleView(i);
        });
    }

    private void setVisibleView(int i) {
        switch (i) {
            case 0:
                shopRadioGroup.setVisibility(View.GONE);
                radioGroup.setVisibility(View.VISIBLE);
                containersCountLayout.setVisibility(View.VISIBLE);
                outletContainersLayout.setVisibility(View.VISIBLE);
                break;
            case 1:
                radioGroup.setVisibility(View.GONE);
                containersCountLayout.setVisibility(View.GONE);
                outletContainersLayout.setVisibility(View.GONE);
                shopRadioGroup.setVisibility(View.VISIBLE);
                break;
            case 2:
                shopRadioGroup.setVisibility(View.GONE);
                radioGroup.setVisibility(View.VISIBLE);
                containersCountLayout.setVisibility(View.GONE);
                outletContainersLayout.setVisibility(View.GONE);
                break;
            case 3:
                shopRadioGroup.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.GONE);
                containersCountLayout.setVisibility(View.GONE);
                outletContainersLayout.setVisibility(View.GONE);
                break;
        }
    }

    private void initViews(View view) {
        questionnaire_second_third_fragment_button = view.findViewById(R.id.questionnaire_second_third_fragment_button);
        dropList = view.findViewById(R.id.questionnaire_second_second__drop_list);
        radioGroup = view.findViewById(R.id.questionnaire_second_second__radio_group);
        shopRadioGroup = view.findViewById(R.id.questionnaire_second_second__shop_radio_group);
        outletContainersLayout = view.findViewById(R.id.questionnaire_second_third_fragment_outlet_containers_count_layout);
        containersCountLayout = view.findViewById(R.id.questionnaire_second_third_fragment_container_number_layout);
        setAdapter();
    }

    private void setAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, generateTextDropList());
        dropList.setAdapter(adapter);
    }
}

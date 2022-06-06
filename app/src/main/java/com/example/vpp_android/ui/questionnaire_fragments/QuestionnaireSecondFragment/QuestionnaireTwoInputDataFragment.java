package com.example.vpp_android.ui.questionnaire_fragments.QuestionnaireSecondFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.vpp_android.R;
import com.example.vpp_android.domain.products_classes.QuestionnaireDataOutletNew;

public class QuestionnaireTwoInputDataFragment extends Fragment {


    public static final String BUNDLE_KEY = "SECOND_QUEST_BUNDLE_KEY";
    QuestionnaireDataOutletNew questionnaireDataOutletNew = new QuestionnaireDataOutletNew();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.questionnaire_second_input_data_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questionnaireDataOutletNew = getArguments().getParcelable(BUNDLE_KEY);
    }
}

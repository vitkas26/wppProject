package com.example.vpp_android.QuestionnaireFragments.QuestionnaireSecondFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.vpp_android.QuestionnaireActivity;
import com.example.vpp_android.QuestionnaireFragments.QuestionnaireListener;
import com.example.vpp_android.R;

public class QuestionnaireSecondFragment extends Fragment {

    private LinearLayout questionnaireSecondTextRu;
    private LinearLayout questionnaireSecondTextKg;
    private int langId;
    private Button questionnaire_second__button_kg;
    private Button questionnaire_second__button_ru;
    private QuestionnaireListener questionnaireListener;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.questionnaireListener = (QuestionnaireListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.questionnaire_second_fragment, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionnaireSecondTextKg = view.findViewById(R.id.questionnaire_second__text_kg);
        questionnaireSecondTextRu = view.findViewById(R.id.questionnaire_second__text_ru);
        questionnaire_second__button_kg = view.findViewById(R.id.questionnaire_second__button_kg);
        questionnaire_second__button_ru = view.findViewById(R.id.questionnaire_second__button_ru);

        questionnaire_second__button_kg.setOnClickListener(v -> questionnaireListener.chooseQuestionnaire(21));
        questionnaire_second__button_ru.setOnClickListener(v -> questionnaireListener.chooseQuestionnaire(21));

        langId = getArguments().getInt(QuestionnaireActivity.POLL_LANG_KEY);

        if (langId == 0){
            questionnaireSecondTextKg.setVisibility(View.GONE);
        }else {
            questionnaireSecondTextRu.setVisibility(View.GONE);
        }
    }
}

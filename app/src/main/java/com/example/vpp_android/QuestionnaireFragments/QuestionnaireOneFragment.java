package com.example.vpp_android.QuestionnaireFragments;

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
import com.example.vpp_android.R;

public class QuestionnaireOneFragment extends Fragment {

    private LinearLayout questionnaireOneTextRu;
    private LinearLayout questionnaireOneTextKg;
    private int langId;
    private Button questionnaire_one__button_kg;
    private Button questionnaire_one__button_ru;
    private QuestionnaireListener questionnaireListener;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.questionnaireListener = (QuestionnaireListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.questionnaire_one_fragment, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionnaireOneTextKg = view.findViewById(R.id.questionnaire_one__text_kg);
        questionnaireOneTextRu = view.findViewById(R.id.questionnaire_one__text_ru);
        questionnaire_one__button_kg = view.findViewById(R.id.questionnaire_one__button_kg);
        questionnaire_one__button_ru = view.findViewById(R.id.questionnaire_one__button_ru);

        questionnaire_one__button_kg.setOnClickListener(v -> questionnaireListener.chooseQuestionnaire(11));
        questionnaire_one__button_ru.setOnClickListener(v -> questionnaireListener.chooseQuestionnaire(11));

        langId = getArguments().getInt(QuestionnaireActivity.POLL_LANG_KEY);

        if (langId == 0){
            questionnaireOneTextKg.setVisibility(View.GONE);
        }else {
            questionnaireOneTextRu.setVisibility(View.GONE);
        }
    }
}

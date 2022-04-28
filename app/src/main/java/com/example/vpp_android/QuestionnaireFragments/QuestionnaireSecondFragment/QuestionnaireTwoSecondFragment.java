package com.example.vpp_android.QuestionnaireFragments.QuestionnaireSecondFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.vpp_android.QuestionnaireFragments.QuestionnaireListener;
import com.example.vpp_android.R;

public class QuestionnaireTwoSecondFragment extends Fragment {

    private QuestionnaireListener questionnaireListener;
    private Button questionnaire_two_second_button;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.questionnaireListener = (QuestionnaireListener) context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.questionnaire_two_second_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionnaire_two_second_button = view.findViewById(R.id.questionnaire_two_second_button);

        questionnaire_two_second_button.setOnClickListener(v -> questionnaireListener.chooseQuestionnaire(22));
    }
}

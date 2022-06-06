package com.example.vpp_android.ui.questionnaire_fragments.QuestionnaireSecondFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.vpp_android.ui.questionnaire_fragments.QuestionnaireListener;
import com.example.vpp_android.R;

public class QuestionnaireTwoSecondFragment extends Fragment {

    private QuestionnaireListener questionnaireListener;
    private Button questionnaire_two_second_button;
    private EditText phoneNumberEditText;
    private EditText authorNameEditText;
    private static final int poleId = 2;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.questionnaireListener = (QuestionnaireListener) context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.questionnaire_second_second_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setClickListeners();
    }

    private void setClickListeners() {
        questionnaire_two_second_button.setOnClickListener(v -> {
            if (!checkEmptyData()) {
                questionnaireListener.sendDataSecondFragment(Double.parseDouble(phoneNumberEditText.getText().toString()), authorNameEditText.getText().toString(), poleId);
                questionnaireListener.chooseQuestionnaire(22);
            }
            else
                Toast.makeText(requireContext(), "Не все поля заполнены", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean checkEmptyData() {
        return phoneNumberEditText.getText().toString().isEmpty() && authorNameEditText.getText().toString().isEmpty();
    }

    private void initViews(View view) {
        questionnaire_two_second_button = view.findViewById(R.id.questionnaire_second_second_button);
        authorNameEditText = view.findViewById(R.id.questionnaire_second_second_phone_author_edit_text);
        phoneNumberEditText = view.findViewById(R.id.questionnaire_second_second_phone_author_edit_text);
    }


}

package com.example.vpp_android.ui.questionnaire_fragments.QuestionaireOneFragments;

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

public class QuestionnaireOneSecondFragment extends Fragment {

    private static final int poleId = 1;
    private QuestionnaireListener questionnaireListener;
    private Button questionnaire_one_second_button;
    private EditText phoneNumberEditText;
    private EditText authorEditText;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.questionnaireListener = (QuestionnaireListener) context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.questionnaire_one_second_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    private void initViews(View view) {
        phoneNumberEditText = view.findViewById(R.id.questionnaire_one_second_phone_author_edit_text);
        authorEditText = view.findViewById(R.id.questionnaire_one_second_phone_author_edit_text);
        questionnaire_one_second_button = view.findViewById(R.id.questionnaire_one_second_button);
        initListener();
    }

    private void initListener() {
        questionnaire_one_second_button.setOnClickListener(v -> {
            checkEntries();
            if (checkEntries()) {
                questionnaireListener.sendDataSecondFragment(Double.parseDouble(phoneNumberEditText.getText().toString()), authorEditText.getText().toString(), poleId);
                questionnaireListener.chooseQuestionnaire(12);
            } else {
                Toast.makeText(requireContext(), "Не введены данные сотрудника", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private boolean checkEntries() {
        return !phoneNumberEditText.getText().toString().isEmpty() || !authorEditText.getText().toString().isEmpty();
    }
}

package com.example.vpp_android.QuestionnaireFragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.vpp_android.InputData;
import com.example.vpp_android.QuestionnaireActivity;
import com.example.vpp_android.R;

public class QuestionnaireContainer extends AppCompatActivity implements QuestionnaireListener {

    private FrameLayout questionnaire_container;
    private int langId;
    private int questionnaireId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire_container);

        questionnaire_container = findViewById(R.id.questionnaire_container);

        Intent intent = getIntent();
        langId = intent.getIntExtra(QuestionnaireActivity.POLL_LANG_KEY, -1);
        questionnaireId = intent.getIntExtra(QuestionnaireActivity.POLL_ID_KEY, -1);

        if (savedInstanceState == null){
            openPollFragment();
        }
    }

    private void openPollFragment() {
        switch (questionnaireId){
            case 0:
                Bundle bundle = new Bundle();
                bundle.putInt(QuestionnaireActivity.POLL_LANG_KEY, langId);
                Fragment questionnaireOne = new QuestionnaireOneFragment();
                questionnaireOne.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.questionnaire_container, questionnaireOne)
                        .commit();
        }
    }

    @Override
    public void chooseQuestionnaire(int id) {
        switch (id){
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.questionnaire_container, new QuestionnaireOneSecondFragment())
                        .addToBackStack("QuestionnaireOneFragment").commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.questionnaire_container, new QuestionnaireOneThirdFragment())
                        .addToBackStack("QuestionnaireOneThirdFragment").commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.questionnaire_container, new QuestionnaireInputDataFragment())
                        .addToBackStack("QuestionnaireOneThirdFragment").commit();
                break;
        }
    }
}

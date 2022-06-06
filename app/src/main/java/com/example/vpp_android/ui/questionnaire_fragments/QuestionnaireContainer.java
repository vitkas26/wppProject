package com.example.vpp_android.ui.questionnaire_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.vpp_android.domain.products_classes.QuestionnaireDataNew;
import com.example.vpp_android.domain.products_classes.QuestionnaireDataOutletNew;
import com.example.vpp_android.ui.activities.MainActivity;
import com.example.vpp_android.ui.activities.QuestionnaireActivity;
import com.example.vpp_android.ui.questionnaire_fragments.QuestionaireOneFragments.QuestionnaireOneFragment;
import com.example.vpp_android.ui.questionnaire_fragments.QuestionaireOneFragments.QuestionnaireOneInputDataFragment;
import com.example.vpp_android.ui.questionnaire_fragments.QuestionaireOneFragments.QuestionnaireOneSecondFragment;
import com.example.vpp_android.ui.questionnaire_fragments.QuestionaireOneFragments.QuestionnaireOneThirdFragment;
import com.example.vpp_android.ui.questionnaire_fragments.QuestionnaireSecondFragment.QuestionnaireTwoFragment;
import com.example.vpp_android.ui.questionnaire_fragments.QuestionnaireSecondFragment.QuestionnaireTwoInputDataFragment;
import com.example.vpp_android.ui.questionnaire_fragments.QuestionnaireSecondFragment.QuestionnaireTwoThirdFragment;
import com.example.vpp_android.ui.questionnaire_fragments.QuestionnaireSecondFragment.QuestionnaireTwoSecondFragment;
import com.example.vpp_android.R;

public class QuestionnaireContainer extends AppCompatActivity implements QuestionnaireListener {

    private FrameLayout questionnaire_container;
    //private QuestionnaireData questionnaireData = new QuestionnaireData();
//    private QuestionnaireDataOutlet questionnaireData = new QuestionnaireDataOutlet();
    private QuestionnaireDataNew questionnaireDataNew = new QuestionnaireDataNew();
    private QuestionnaireDataOutletNew questionnaireDataOutletNew = new QuestionnaireDataOutletNew();
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

        if (savedInstanceState == null) {
            openPollFragment();
        }
    }

    private void openPollFragment() {
        Bundle bundle = new Bundle();

        switch (questionnaireId) {
            case 0:
                bundle.putInt(QuestionnaireActivity.POLL_LANG_KEY, langId);
                Fragment questionnaireOne = new QuestionnaireOneFragment();
                questionnaireOne.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.questionnaire_container, questionnaireOne)
                        .commit();
                break;
            case 1:
                bundle.putInt(QuestionnaireActivity.POLL_LANG_KEY, langId);
                Fragment questionnaireTwo = new QuestionnaireTwoFragment();
                questionnaireTwo.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.questionnaire_container, questionnaireTwo)
                        .commit();
                break;
        }
    }

    @Override
    public void chooseQuestionnaire(int id) {
        Bundle bundle = new Bundle();

        switch (id) {
            case 11:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.questionnaire_container, new QuestionnaireOneSecondFragment())
                        .addToBackStack("QuestionnaireOneFragment").commit();
                break;
            case 12:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.questionnaire_container, new QuestionnaireOneThirdFragment())
                        .addToBackStack("QuestionnaireOneSecondFragment").commit();
                break;
            case 13:
                bundle.putParcelable(QuestionnaireOneInputDataFragment.BUNDLE_KEY, questionnaireDataNew);
                Fragment questionnaireOneInputDataFragment = new QuestionnaireOneInputDataFragment();
                questionnaireOneInputDataFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.questionnaire_container, questionnaireOneInputDataFragment)
                        .addToBackStack("QuestionnaireOneThirdFragment").commit();
                break;
            case 21:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.questionnaire_container, new QuestionnaireTwoSecondFragment())
                        .addToBackStack("QuestionnaireSecondThirdFragment").commit();
                break;
            case 22:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.questionnaire_container, new QuestionnaireTwoThirdFragment())
                        .addToBackStack("QuestionnaireTwoSecondFragment").commit();
                break;
            case 23:
                bundle.putParcelable(QuestionnaireTwoInputDataFragment.BUNDLE_KEY, questionnaireDataOutletNew);
                Fragment questionnaireTwoInputDataFragment = new QuestionnaireTwoInputDataFragment();
                questionnaireTwoInputDataFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.questionnaire_container, questionnaireTwoInputDataFragment)
                        .addToBackStack("QuestionnaireSecondInputDataFragment").commit();
                break;
            default:
                FragmentManager fm = getSupportFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void sendDataThirdFragment(int producerType, int enterpriseStatus, int isActive, int id) {
        switch (id) {
            case 1:
                questionnaireDataNew.setProducerType(producerType);
                questionnaireDataNew.setEnterpriseStatus(enterpriseStatus);
                questionnaireDataNew.setActive(isActive);
                break;
            case 2:
                questionnaireDataOutletNew.setImplementationType(producerType);
                questionnaireDataOutletNew.setOutletStatus(enterpriseStatus);
                questionnaireDataOutletNew.setActive(isActive);
                break;
            default:
                try {
                    throw new Exception("Illegal pole id");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

    }

    @Override
    public void sendDataSecondFragment(Double phone, String managerName, int id) {
        switch (id) {
            case 1:
                questionnaireDataNew.setPhone(phone);
                questionnaireDataNew.setManagerName(managerName);
                break;
            case 2:
                questionnaireDataOutletNew.setPhone(phone);
                questionnaireDataOutletNew.setManagerName(managerName);
        }

    }
}

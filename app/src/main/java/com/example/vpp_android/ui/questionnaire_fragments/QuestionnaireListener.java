package com.example.vpp_android.ui.questionnaire_fragments;

public interface QuestionnaireListener {
    void chooseQuestionnaire(int id);
    void sendDataThirdFragment(int producerType, int enterpriseType, int isActive, int id);
    void sendDataSecondFragment(Double phone, String managerName, int id);
}

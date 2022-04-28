package com.example.vpp_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.vpp_android.QuestionnaireFragments.QuestionnaireContainer;

import java.util.ArrayList;
import java.util.List;

public class QuestionnaireActivity extends AppCompatActivity {

    private Button questionnaireOne, questionnaireTwo;
    private Spinner questionnaireSpinner;
    private int langId;
    private int pollId;
    public static final String POLL_LANG_KEY = "pollLang";
    public static final String POLL_ID_KEY = "pollId";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        questionnaireOne = findViewById(R.id.questionnaire_one);
        questionnaireTwo = findViewById(R.id.questionnaire_two);
        questionnaireSpinner = findViewById(R.id.questionnaire_spinner);

        questionnaireOne.setOnClickListener(v -> {
            pollId = 0;
            Intent intent = new Intent(QuestionnaireActivity.this, QuestionnaireContainer.class);
            intent.putExtra(POLL_LANG_KEY, langId);
            intent.putExtra(POLL_ID_KEY, pollId);
            startActivity(intent);
        });
        questionnaireTwo.setOnClickListener(v -> {
            pollId = 1;
            Intent intent = new Intent(QuestionnaireActivity.this, QuestionnaireContainer.class);
            intent.putExtra(POLL_LANG_KEY, langId);
            intent.putExtra(POLL_ID_KEY, pollId);
            startActivity(intent);
        });

        addDataToSpinner();

        questionnaireSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                langId = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                langId = 0;
            }
        });
    }

    private void addDataToSpinner() {
        List<String> items = new ArrayList<>();
        items.add("Русский");
        items.add("Кыргызкий");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionnaireSpinner.setAdapter(adapter);
    }
}
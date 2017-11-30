package com.questionqate.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.questionqate.CreateQuestion.CreateQuestionActivity;
import com.questionqate.R;

public class LecturerHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_home);
    }

    public void createQuestion(View view) {
        startActivity(new Intent(this, CreateQuestionActivity.class));
    }

    public void viewQuestions(View view) {
    }
}

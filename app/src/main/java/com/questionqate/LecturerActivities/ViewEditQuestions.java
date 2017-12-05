package com.questionqate.LecturerActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.questionqate.R;

public class ViewEditQuestions extends AppCompatActivity {

    RecyclerView view_question_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_questions);

        view_question_list = findViewById(R.id.view_question_list);
    }
}

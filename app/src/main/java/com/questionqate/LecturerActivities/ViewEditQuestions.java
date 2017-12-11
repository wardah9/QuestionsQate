package com.questionqate.LecturerActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.questionqate.Adapters.ViewLecturerQuestionsListAdapter;
import com.questionqate.Pojo.Teacher;
import com.questionqate.R;

public class ViewEditQuestions extends AppCompatActivity {

    RecyclerView view_question_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_questions);

//        view_question_list = findViewById(R.id.view_question_list);
//        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
//        view_question_list.setLayoutManager(linearLayoutManager);
//
//        if (Teacher.INSTANCE.getCurrentTeacher().getSubjects() != null) {
//            view_question_list.setAdapter(new ViewLecturerQuestionsListAdapter(Teacher.INSTANCE.getCurrentTeacher().getSubjects(), ViewEditQuestions.this));
//        } else
//            Toast.makeText(this, "no subjects ", Toast.LENGTH_SHORT).show();
    }
}

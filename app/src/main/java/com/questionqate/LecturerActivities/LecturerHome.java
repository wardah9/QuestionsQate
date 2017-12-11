package com.questionqate.LecturerActivities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.questionqate.R;
import com.questionqate.StudentProfile.Profile;

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
        startActivity(new Intent(this, ViewQuestionsList.class));
    }

    public void requestNewSubject(View view) {

        final Dialog dialog = new Dialog(LecturerHome.this);
        dialog.setContentView(R.layout.custom_diaog_luct);
        dialog.setCancelable(false);
        EditText newSubjectName = dialog.findViewById(R.id.newSubjectName);
        Button send = dialog.findViewById(R.id.send);
        TextView exit = dialog.findViewById(R.id.exit);

        send.setOnClickListener(view1 -> {

            if (newSubjectName.getText().length() > 0) {
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Subject should not be empty !", Toast.LENGTH_SHORT).show();
            }
        });

        exit.setOnClickListener(view12 -> {

            dialog.dismiss();
        });


    }
}

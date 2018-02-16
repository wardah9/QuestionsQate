package com.questionqate.LecturerActivities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.questionqate.R;
import com.questionqate.StudentProfile.Profile;

public class LecturerHome extends AppCompatActivity {

    EditText newSubjectName;
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
        dialog.show();
        newSubjectName = dialog.findViewById(R.id.newSubjectName);
        Button send = dialog.findViewById(R.id.send);
        TextView exit = dialog.findViewById(R.id.exit);

        send.setOnClickListener(view1 -> {

            if (newSubjectName.getText().length() > 0) {
                dialog.dismiss();
                sendEmail();

            } else {
                Toast.makeText(this, "Subject should not be empty !", Toast.LENGTH_SHORT).show();
            }
        });

        exit.setOnClickListener(view12 -> {

            dialog.dismiss();
        });


    }

    protected void sendEmail() {

        String[] TO = {"wardah9658@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Adding new Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Admin,\n" +
                "this massege regarding to request adding a subject name \n" +
                "\n" +
                ""+ newSubjectName.getText().toString()+ "\n" +
                "\n" +
                "i accept any penalty after adding the question..\n" +
                "\n"+
                "with regards.");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();

            Toast.makeText(this, "Finished sending email...", Toast.LENGTH_SHORT).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(LecturerHome.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}

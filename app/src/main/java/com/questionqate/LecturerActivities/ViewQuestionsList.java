package com.questionqate.LecturerActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.questionqate.Adapters.TeacherLevel_viewListAdapter;
import com.questionqate.Adapters.TeacherSubjectListAdapter;
import com.questionqate.Adapters.ViewLecturerQuestionsListAdapter;
import com.questionqate.Dialog.LoadingDialog;
import com.questionqate.Interface.LecturerChoices;
import com.questionqate.Pojo.Teacher;
import com.questionqate.R;
import com.questionqate.Utilties.EventBus;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewQuestionsList extends AppCompatActivity implements LecturerChoices {

    RecyclerView ViewSubjectList;
    RecyclerView ViewLevelsList;
    RecyclerView ViewQuestionsList;

    String subjectchoice;
    int levelchoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.INSTANCE.addLecturerListner(this);
        setContentView(R.layout.activity_view_questions_list);




        LoadingDialog dialog = new LoadingDialog(this, "Loading... wait!");
        dialog.setCancelable(false);
        dialog.show();

        if (Teacher.INSTANCE.getCurrentTeacher().getSubjects() != null) {
            ViewSubjectList = findViewById(R.id.ViewSubjectList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            ViewSubjectList.setLayoutManager(linearLayoutManager);
            ViewSubjectList.setAdapter(new TeacherSubjectListAdapter(Teacher.INSTANCE.getCurrentTeacher().getSubjects(), this));
        } else {
            Toast.makeText(this, "no subjects ", Toast.LENGTH_SHORT).show();
        }

        ViewLevelsList = findViewById(R.id.ViewLevelsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ViewLevelsList.setLayoutManager(linearLayoutManager);
        ViewLevelsList.setAdapter(new TeacherLevel_viewListAdapter(this));

        dialog.dismiss();


    }

    private void viewQuestionsList(String subjectchoice, int levelchoice) {

        ViewSubjectList.setVisibility(View.GONE);
        ViewLevelsList.setVisibility(View.GONE);

        LoadingDialog dialog = new LoadingDialog(this, "Loading... wait!");
        dialog.setCancelable(false);
        dialog.show();

        AndroidNetworking.post("https://us-central1-questionsqate-9a3d7.cloudfunctions.net/getQuestions")
                .addBodyParameter("subject_name", subjectchoice)
                .addBodyParameter("level", String.valueOf(levelchoice))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {


                        JSONArray questions = new JSONArray();
                        questions = response;
                        questions.remove(0);

                        ViewQuestionsList = findViewById(R.id.ViewQuestionsList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewQuestionsList.this, LinearLayoutManager.VERTICAL, false);
                        ViewQuestionsList.setLayoutManager(linearLayoutManager);
                        System.out.println(questions.toString());
                        ViewQuestionsList.setAdapter(new ViewLecturerQuestionsListAdapter(questions,ViewQuestionsList.this));

                        dialog.dismiss();

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }

    @Override
    public void onLecturerSubjectChange(String SubjectChoice) {
        System.out.println("Subject is :" + SubjectChoice);
        subjectchoice = SubjectChoice;

        if (levelchoice > 0) {

            viewQuestionsList(subjectchoice, levelchoice);
        } else {
            Toast.makeText(this, "Choose level", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onLecturerLevelChange(int LevelChoice) {
        System.out.println("Level is : " + (LevelChoice+1));
        levelchoice = (LevelChoice+1);

        if (subjectchoice.length() > 0) {

            viewQuestionsList(subjectchoice, levelchoice);
        } else {
            Toast.makeText(this, "Choose subject", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.INSTANCE.removeLecturerListner(this);
    }
}

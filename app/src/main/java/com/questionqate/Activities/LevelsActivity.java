package com.questionqate.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.questionqate.Pojo.QuestionList;
import com.questionqate.Pojo.Global_Strings;
import com.questionqate.R;
import com.questionqate.Pojo.Question;

import org.json.JSONArray;

import io.reactivex.Observable;

public class LevelsActivity extends AppCompatActivity {

    Global_Strings global;
    private AndroidNetworking ndroidNetworking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        global = new Global_Strings();




        ndroidNetworking.post("https://us-central1-questionsqate-9a3d7.cloudfunctions.net/getSubjects")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //  Log.d("questionArray",response.toString());
                        System.out.print(response.length());
                        for(int i=1; i<=response.length(); i++){
                            int finalI = i;
//        .map(e->e.getJSONArray("Questions"))
//                                    .doOnNext(e->e.remove(0))
                            Observable.fromArray(response)
                                    .map(e->e.getJSONObject(1).getJSONArray("levels")
                                            .getJSONObject(finalI)) //TODO add dynamic 1,2,3 for level
                                    .doOnNext(e->QuestionList.getInstance().getQuestionList()
                                            .add(new Question(e.getJSONArray("Questions"),
                                                    e.getInt("StrikeTime"))))
                                    .doOnNext(e->System.out.println(QuestionList.getInstance().getQuestionList().toArray()))
                                    .subscribe();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public void OnLowLevelClicked(View view) {
       global.setLevel_status("low");
        startActivity(new Intent(this, QuestionsMainView.class));
    }

    public void OnMediumLevelClicked(View view) {
        global.setLevel_status("medium");
        startActivity(new Intent(this, QuestionsMainView.class));
    }

    public void OnHighLevelClicked(View view) {
        global.setLevel_status("high");
        startActivity(new Intent(this, QuestionsMainView.class));
    }
}

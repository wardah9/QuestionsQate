package com.questionqate.Questions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Chronometer;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.questionqate.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class QuestionsMainView extends AppCompatActivity {

    private AndroidNetworking ndroidNetworking;

    private RecyclerView mRecyclerView;
    private Orientation mOrientation = Orientation.VERTICAL;

    public enum Orientation {
        VERTICAL
    }

    public enum OrderStatus {

        COMPLETED,
        ACTIVE,
        INACTIVE;

    }

    Chronometer chronometer;
    TextView choronometer_text;
    NumberFormat f = new DecimalFormat("00");
    int maxCounter=20;
    int minCounter=0;
    int currentCOunter=maxCounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_list);

        chronometer=findViewById(R.id.chrono_counter);
        choronometer_text=findViewById(R.id.counter_textview);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

        System.out.print(QuestionList.getInstance().questionList.get(0).getQuestions());
         mRecyclerView.setAdapter(new QuestionAdapter(QuestionList.getInstance().getQuestionList().get(0),mRecyclerView));
         //chronometer.setBase(0);


        chronometer.start();

        chronometer.setOnChronometerTickListener(chronometer -> {
            String textCounter="00:"+f.format(currentCOunter);
            if(currentCOunter>minCounter){
                currentCOunter--;
            }
            System.out.print(textCounter);
            choronometer_text.setText(textCounter);

        });

    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }





}



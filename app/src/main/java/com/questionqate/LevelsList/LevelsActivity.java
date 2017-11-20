package com.questionqate.LevelsList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.questionqate.modle.Global_Strings;
import com.questionqate.R;
import com.questionqate.TimelineQuestionsList.QuestionsList;

public class LevelsActivity extends AppCompatActivity {

    Global_Strings global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        global = new Global_Strings();

    }

    public void OnLowLevelClicked(View view) {
       global.setLevel_status("low");
        startActivity(new Intent(this, QuestionsList.class));
    }

    public void OnMediumLevelClicked(View view) {
        global.setLevel_status("medium");
        startActivity(new Intent(this, QuestionsList.class));
    }

    public void OnHighLevelClicked(View view) {
        global.setLevel_status("high");
        startActivity(new Intent(this, QuestionsList.class));
    }
}

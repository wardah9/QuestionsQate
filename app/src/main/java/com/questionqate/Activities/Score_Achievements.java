package com.questionqate.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.questionqate.Adapters.Score_AchievementsAdapter;
import com.questionqate.Pojo.Global_Strings;
import com.questionqate.R;

import org.json.JSONArray;

public class Score_Achievements extends AppCompatActivity {

    RecyclerView achievements_score_list;
    AndroidNetworking ndroidNetworking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score__achievements);

        achievements_score_list = findViewById(R.id.achievements_score_list);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        achievements_score_list.setLayoutManager(mLayoutManager);
        achievements_score_list.setItemAnimator(new DefaultItemAnimator());
//        achievements_score_list.setHasFixedSize(true);


        ndroidNetworking.post("https://us-central1-questionsqate-9a3d7.cloudfunctions.net/getAchievements")
                .addBodyParameter("student_id", Global_Strings.INSTANCE.getStudent_UID_firebase())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        achievements_score_list.setAdapter(new Score_AchievementsAdapter(Score_Achievements.this,response));
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Achievement Error:",anError.getErrorDetail()+"\n"+anError.getErrorBody());
                    }
                });

    }

//    private LinearLayoutManager getLinearLayoutManager() {
//        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//    }

}



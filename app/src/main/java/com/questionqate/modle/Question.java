package com.questionqate.modle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;

/**
 * Created by CodeCrazy on 11/20/17.
 */

public class Question {
    private int i;
    JSONArray questions;
    int id;
    String questoin;
    String type;

    public enum questionStatus {

        COMPLETED,
        ACTIVE,
        INACTIVE,
        InCompleted

    }

    public questionStatus questionStatus(int questionIndex) throws JSONException {

            return (questionStatus) questions.getJSONObject(questionIndex).get("status");

    }

    public void setStatus(int questionIndex,questionStatus status) throws JSONException {

        questions.getJSONObject(questionIndex).put("status",status);

    }

    public Question(JSONArray questions) {

        for(int i=0;i<questions.length();i++){
            try {
                if(i==0){
                    questions.getJSONObject(i).put("status",questionStatus.ACTIVE);
                }else{
                    questions.getJSONObject(i).put("status",questionStatus.INACTIVE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println(questions);

        this.questions = questions;


    }

    public JSONArray getQuestions() {
        return questions;
    }


    public int getId() {
        return id;
    }

    public String getQuestoin() {
        return questoin;
    }

    public String getType() {
        return type;
    }
}

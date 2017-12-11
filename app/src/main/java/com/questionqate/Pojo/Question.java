package com.questionqate.Pojo;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by CodeCrazy on 11/20/17.
 */

public class Question {
    private int i;
    JSONArray questions;
    int id;
    String questoin;
    String type;
    int strikeTime;

    public int getStrikeTime() {
        return strikeTime;
    }

    public enum questionStatus {

        COMPLETED,
        ACTIVE,
        INACTIVE,
        EndOFQuestoins

    }

    public questionStatus questionStatus(int questionIndex) throws JSONException {

            return (questionStatus) questions.getJSONObject(questionIndex).get("status");


    }

    public void setStatus(int questionIndex,questionStatus status) throws JSONException {

        if(questionIndex<questions.length()){
            questions.getJSONObject(questionIndex).put("status",status);

        }

    }

    public questionStatus getStatus(int questionIndex) throws JSONException {

           return (questionStatus) questions.getJSONObject(questionIndex).get("status");

    }

    public Question(JSONArray questions,int StrikeTime) {

           questions.remove(0);
        for(int i=0;i<questions.length();i++){
            try {
                if(i==0){
                    questions.getJSONObject(i).put("status",questionStatus.ACTIVE);
                }else if(questions.getJSONObject(i).getString("type").equals("end")){
                    questions.getJSONObject(i).put("status",questionStatus.EndOFQuestoins);
                }else{
                    questions.getJSONObject(i).put("status",questionStatus.INACTIVE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        System.out.println(questions);

        this.questions = questions;
        this.strikeTime = StrikeTime;

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

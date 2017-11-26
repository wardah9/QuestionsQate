package com.questionqate.Pojo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CodeCrazy on 11/20/17.
 */

public class QuestionList {

    private QuestionList() {}
    private static QuestionList instance;
    public static QuestionList getInstance() {
        if (instance == null) instance = new QuestionList();

        return instance;
    }



    public static void setNewInstance(QuestionList questionList){
        instance = questionList;
    }

    public List<Question> questionList = new ArrayList<>();

    public List<Question> getQuestionList() {
        return questionList;
    }

    public JSONObject oneItemQuestionList(int whichlevel, int postion){
        try {
           return questionList.get(whichlevel).getQuestions().getJSONObject(postion);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}

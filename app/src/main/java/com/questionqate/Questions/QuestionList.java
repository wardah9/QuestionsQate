package com.questionqate.Questions;

import com.questionqate.modle.Question;

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

    public List<Question> questionList = new ArrayList<>();

    public List<Question> getQuestionList() {
        return questionList;
    }
}

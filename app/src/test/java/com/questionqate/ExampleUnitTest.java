package com.questionqate;

import com.questionqate.Pojo.Question;
import com.questionqate.Pojo.QuestionHelper;

import org.json.JSONArray;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void addQuestions()  {
        JSONArray jsonArray=new JSONArray();
        jsonArray.put(QuestionHelper.INSTANCE.addChoice(1,"test"));
        QuestionHelper.currentQuestion cr=
                new QuestionHelper.currentQuestion("test","true_fals",jsonArray);
        QuestionHelper.INSTANCE.toString(cr);


    }
}
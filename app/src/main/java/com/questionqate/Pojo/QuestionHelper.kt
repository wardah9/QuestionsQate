package com.questionqate.Pojo

import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by anarose on 11/30/17.
 */

object QuestionHelper {

    data class currentQuestion(var subject_name: String, var level_id: String,var question: String,
                               var question_type: String,var choice: JSONArray)


    fun addChoice(id:Int, choice:String) : JSONObject{
        var choice= JSONObject()
        choice.put("choice",choice)
        choice.put("id",id)
        return choice
    }

    fun addChoice(id:Int, choice:Int) : JSONObject{
        var choice= JSONObject()
        choice.put("choice",choice)
        choice.put("id",id)
        return choice
    }

    fun toString(currentQuestion: currentQuestion): String {
        return currentQuestion.toString()
    }



}

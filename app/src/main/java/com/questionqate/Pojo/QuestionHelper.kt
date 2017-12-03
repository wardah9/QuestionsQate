package com.questionqate.Pojo

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by anarose on 11/30/17.
 */

object QuestionHelper {

    var subject_name: String = ""
    var level_id: String = ""

    data class currentQuestion(var question: String,
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


    fun toJson(currentQuestion: currentQuestion){
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonPerson: String = gson.toJson(currentQuestion)
        System.out.println(jsonPerson)

    }
    fun toApi(currentQuestion: currentQuestion) {

        var toAPi = JSONObject()
        toAPi.put("subject_name", subject_name)
        toAPi.put("level_id", level_id)
        toAPi.put("question",currentQuestion.question)
        toAPi.put("question_type",currentQuestion.question_type)
        toAPi.put("choices",currentQuestion.choice)
    }

    fun sendQuestionToAPI(){

    }



}


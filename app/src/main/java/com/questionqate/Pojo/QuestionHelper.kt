package com.questionqate.Pojo

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.JsonParser
import css.fingerprint.Networking.OkhttpObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.util.HalfSerializer.onNext
import io.reactivex.schedulers.Schedulers
import okhttp3.FormBody
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by anarose on 11/30/17.
 */

object QuestionHelper {

    var subject_name: String = ""
    var level_id: String = ""

    data class currentQuestion(var question: String,
                               var question_type: String, var choice: JSONArray, var correctAnswerid: JSONArray)



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




    fun sendQuestionToAPI(){

    }



}


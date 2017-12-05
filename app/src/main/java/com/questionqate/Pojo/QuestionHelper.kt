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
                               var question_type: String,
                               var choice: JSONArray,
                               var correctAnswerid: JSONArray)

    fun addChoice(id: Int, choice: String): JSONObject {
        var choice = JSONObject()
        choice.put("choice", choice)
        choice.put("id", id)
        return choice
    }

    fun addChoice(id: Int, choice: Int): JSONObject {
        var choice = JSONObject()
        choice.put("choice", choice)
        choice.put("id", id)
        return choice
    }


    fun toApi(currentQuestion: currentQuestion) {

        val toAPi = FormBody.Builder()
        toAPi.add("subject_name", subject_name)
        toAPi.add("level_id", level_id)
        toAPi.add("question", currentQuestion.question)
        toAPi.add("question_type", currentQuestion.question_type)
        toAPi.add("choices", currentQuestion.choice.toString())
        toAPi.add("correct_answers", currentQuestion.correctAnswerid.toString())
        System.out.println(" current question " + toAPi.toString())

        AndroidNetworking.post("https://us-central1-questionsqate-9a3d7.cloudfunctions.net/addQuestion")
                .addBodyParameter("subject_name", subject_name)
                .addBodyParameter("level_id", level_id)
                .addBodyParameter("question", currentQuestion.question)
                .addBodyParameter("question_type", currentQuestion.question_type)
                .addBodyParameter("choices", currentQuestion.choice.toString())
                .addBodyParameter("correct_answers", currentQuestion.correctAnswerid.toString())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {

                    override fun onResponse(response: JSONObject) {
                        System.out.println(response)
                    }

                    override fun onError(anError: ANError) {
                        System.out.println(anError.printStackTrace())
                    }
                })
//
//        OkhttpObservable.post("https://us-central1-questionsqate-9a3d7.cloudfunctions.net/addQuestion",toAPi)
//                .subscribeOn(Schedulers.io()) // do the request in background thread
//                .observeOn(AndroidSchedulers.mainThread()) // when the request is completed bring the result to main thread or GUI
//                .doOnNext { e-> System.out.println("addd question ${e}") }
//                .subscribe()

    }

    fun sendQuestionToAPI() {

    }


}


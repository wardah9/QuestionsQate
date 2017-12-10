package com.questionqate.Activities

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.questionqate.Pojo.QuestionList
import com.questionqate.Pojo.Global_Strings
import com.questionqate.R
import com.questionqate.Pojo.Question
import com.questionqate.Utilties.LoadingDialog

import org.json.JSONArray

import java.util.Arrays

import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_levels.*
import org.json.JSONObject
import android.preference.PreferenceManager
import android.content.SharedPreferences
import android.opengl.Visibility
import com.androidnetworking.interfaces.JSONObjectRequestListener


class LevelsActivity : AppCompatActivity() {

    internal lateinit var global: Global_Strings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_levels)


        global = Global_Strings

        val dialog = LoadingDialog().init(this, "Loading please wait")
        dialog.show()

        AndroidNetworking.post("https://us-central1-questionsqate-9a3d7.cloudfunctions.net/getSubjects")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject?) {
                        System.out.println(response!!.getJSONObject("JAVA").getJSONArray("levels"))
                        System.out.println("levels array"+response!!.getJSONObject("JAVA").getJSONArray("levels").length())
                        for (i in 1 until response.getJSONObject("JAVA").getJSONArray("levels").length()) {
                            Observable.fromArray(response!!.getJSONObject("JAVA").getJSONArray("levels"))
                                    .map<JSONObject> { e ->
                                        e.getJSONObject(i)
                                    }
                                    .doOnNext { e ->
                                        QuestionList.getInstance().getQuestionList()
                                                .add(Question(e.getJSONArray("Questions"),
                                                        e.getInt("StrikeTime")))
                                    }
                                    .doOnNext { e -> println(" here  " + Arrays.toString(QuestionList.getInstance().getQuestionList().toTypedArray())) }
                                    .subscribe()
                        }
                        dialog.dismiss()
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onError(anError: ANError?) {
                   //     TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })

//                .getAsJSONArray(object : JSONArrayRequestListener {
//
//                    override fun onResponse(response: JSONArray) {
//                        //  Log.d("questionArray",response.toString());
//                        System.out.print(response.length())
//                        for (i in 1..response.length()+1) {
////        .map(e->e.getJSONArray("Questions"))
//                            //                                    .doOnNext(e->e.remove(0))
//                            Observable.fromArray(response)
//                                    .map<JSONObject> { e ->
//                                        e.getJSONObject(0).getJSONArray("levels")
//                                                .getJSONObject(i)
//                                    } //TODO add dynamic 1,2,3 for level
//                                    .doOnNext { e ->
//                                        QuestionList.getInstance().getQuestionList()
//                                                .add(Question(e.getJSONArray("Questions"),
//                                                        e.getInt("StrikeTime")))
//                                    }
//                                    .doOnNext { e -> println(" here  "+Arrays.toString(QuestionList.getInstance().getQuestionList().toTypedArray())) }
//                                    .subscribe()
//
//                            dialog.dismiss()
//                        }
//                    }
//
//                    override fun onError(anError: ANError) {
//
//                    }
//                })


    }

    override fun onResume() {
        super.onResume()
        val intent = intent

        val hideLevel = intent.getIntExtra("finished_level", 3)
        var sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putBoolean("finished_level_"+hideLevel, true)
        editor.commit()




        if(sharedPreferences.getBoolean("finished_level_0", false)){
            level_one_btn.visibility = View.GONE
            level_one_btn.isEnabled = false
        }
        if(sharedPreferences.getBoolean("finished_level_1", false)){
            level_two_btn.visibility = View.GONE
            level_two_btn.isEnabled = false
        }
        if(sharedPreferences.getBoolean("finished_level_2", false)){
            level_three_btn.visibility = View.GONE
            level_three_btn.isEnabled = false
        }

        if(sharedPreferences.getBoolean("finished_level_0", false)&&
                sharedPreferences.getBoolean("finished_level_1", false)&&
                sharedPreferences.getBoolean("finished_level_2", false)){
            activity_level_no_questions.visibility = View.VISIBLE
        }



    }

    fun OnLowLevelClicked(view: View) {
        global.level_status = "low"
        var lowlevel_intent= Intent(this, QuestionsMainView::class.java)
        lowlevel_intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK ; Intent.FLAG_ACTIVITY_CLEAR_TASK
        lowlevel_intent.putExtra("level", 0)
        startActivity(lowlevel_intent)
        finish()
    }

    fun OnMediumLevelClicked(view: View) {
        global.level_status = "medium"
        var intent= Intent(this, QuestionsMainView::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK ; Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("level", 1)
        startActivity(intent)
        finish()

    }

    fun OnHighLevelClicked(view: View) {
        global.level_status = "high"
        var intent= Intent(this, QuestionsMainView::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK ; Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("level", 2)
        startActivity(intent)
        finish()

    }

    override fun onBackPressed() {
        var intent =Intent(this@LevelsActivity,StudentSlideMenu::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK ; Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()


    }
}

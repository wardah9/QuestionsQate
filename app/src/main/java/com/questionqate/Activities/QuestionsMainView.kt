package com.questionqate.Activities

import android.app.Dialog
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.gson.JsonParser
import com.questionqate.Adapters.QuestionAdapter
import com.questionqate.Interface.Exceptions
import com.questionqate.Interface.StrikeTimeInterface
import com.questionqate.Pojo.Global_Strings
import com.questionqate.Pojo.QuestionList
import com.questionqate.R
import com.questionqate.Utilties.EventBus
import com.questionqate.Utilties.LoadingDialog
import css.fingerprint.Networking.OkhttpObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_questions_list.*
import kotlinx.android.synthetic.main.dialogloading.*
import okhttp3.FormBody
import java.text.DecimalFormat
import java.text.NumberFormat

class QuestionsMainView : AppCompatActivity(), StrikeTimeInterface, Exceptions {
    override fun onNetworkException(e: String) {
        TODO("show no internet") //To change body of created functions use File | Settings | File Templates.
    }


    private var mRecyclerView: RecyclerView? = null

    private var user_strike_time = 0
    private var strike_counter = 0


    internal var f: NumberFormat = DecimalFormat("00")
    internal var currentCOunter = 0


    private val linearLayoutManager: LinearLayoutManager
        get() = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    var level=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_list)


         val intent = intent
         level = intent.getIntExtra("level", 0)

        EventBus.addStrikeTimeListener(this)

        mRecyclerView = findViewById(R.id.recyclerView)
        mRecyclerView!!.layoutManager = linearLayoutManager
        mRecyclerView!!.setHasFixedSize(true)

        currentCOunter = QuestionList.getInstance().getQuestionList()[0].strikeTime
        val textCounter = "Level Strike Time " + "00:" + f.format(currentCOunter.toLong())
        counter_textview.text = textCounter

        val textCounter2 = "Your Strike Time " + "00:00"
        user_counter_textview.text = textCounter2



        mRecyclerView!!.adapter = QuestionAdapter(QuestionList.getInstance().getQuestionList()[level])
        chrono_counter.start()


    }

    internal fun ToastAchivement() {

        val li = layoutInflater
        //Getting the View object as defined in the customtoast.xml file
        val layout = li.inflate(R.layout.achivement,
                findViewById<ViewGroup>(R.id.achievement_layout))
        val level = layout.findViewById<View>(R.id.achivement_level_text) as TextView
        level.text = "test"

        //Creating the Toast object
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_LONG
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.view = layout//setting the view of custom toast layout
        toast.show()
    }

    internal fun AchivementNotification() {
        val pIntent = PendingIntent.getActivity(this, System.currentTimeMillis().toInt(), Intent(), 0)

        val uriSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notification = Notification.Builder(this)
                .setContentTitle("Achievement")
                .setContentText("Congratulations,you got new record on level")
                .setSmallIcon(R.drawable.trophy)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.trophy))
                .setSound(uriSound)
                .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, notification)
    }


    override fun onStrike(time: Int) {
        user_strike_time = user_strike_time + time
        val strike = "Your Strike Time " + "00:" + f.format(user_strike_time.toLong())
        user_counter_textview.text = strike
        strike_counter++
        if (strike_counter == 3) {
            ToastAchivement()
            AchivementNotification()
        }

    }


    override fun RemoveStrike() {
        if (strike_counter != 3) {
            // u reached to achivement
            user_strike_time = 0
            val strike = "Your Strike Time " + "00:" + f.format(user_strike_time.toLong())
            user_counter_textview.text = strike
            strike_counter = 0
        }

    }

    override fun onLevelComplete() {
        runOnUiThread {

            if(strike_counter>=3){
                val completeDialog = LoadingDialog().init(this, "Saving Score")
                completeDialog.setCancelable(false)
                completeDialog.show()
                addAchivementToDatabase(completeDialog)
            }else{


                val completeDialog = LoadingDialog().init(this, "Saving Score")
                completeDialog.setCancelable(false)
                completeDialog.show()
                completeDialog.getActionButton()!!.text = "OK"
                completeDialog.getActionButton()!!.visibility = View.VISIBLE
                completeDialog.setResultText("Score Saved")
                completeDialog.hideProgress()
                completeDialog.doAction(View.OnClickListener{

                    completeDialog.dismiss()
                    goTolevels()
                })
            }

        }

    }

    fun getLevelString(level: Int): String {

        when(level)
        {
            0-> return "low"
            1-> return "medium"
            2-> return "high"
        }

        return "low"
    }

    internal fun addAchivementToDatabase(completeDialog: com.questionqate.Dialog.LoadingDialog) {


                val form = FormBody.Builder()
                form.add("student_id", Global_Strings.student_UID_firebase) //Todo add dynamic
                form.add("striketime", user_strike_time.toString())
                form.add("level", getLevelString(level))
                form.add("subject", "java")

                OkhttpObservable
                        .post("https://us-central1-questionsqate-9a3d7.cloudfunctions.net/setNewAchievements",form)
                        .subscribeOn(Schedulers.io())
                        .map { response -> JsonParser().parse(response.body()!!.string()) }
                        .map { jsonObject -> jsonObject.asJsonObject.get("success").asBoolean}
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext { success->


                            completeDialog.getActionButton()!!.text = "OK"
                            completeDialog.getActionButton()!!.visibility = View.VISIBLE
                            completeDialog.setResultText("Score Saved")
                            completeDialog.hideProgress()

                            completeDialog.doAction(View.OnClickListener{
                                completeDialog.dismiss()
                                goTolevels()
                            })

                        }.subscribe()

    }

    fun goTolevels(){
       var intent =Intent(this@QuestionsMainView,LevelsActivity::class.java)
        intent.putExtra("finished_level",level)
        startActivity(intent)
       // finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent =Intent(this@QuestionsMainView,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}



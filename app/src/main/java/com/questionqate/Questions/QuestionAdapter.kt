package com.questionqate.Questions

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.LinearLayout
import com.github.vipulasri.timelineview.TimelineView
import com.google.gson.JsonParser
import com.questionqate.R
import com.questionqate.Utilties.LoadingButton
import com.questionqate.Utilties.VectorDrawableUtils
import com.questionqate.modle.Question
import css.fingerprint.Networking.OkhttpObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.FormBody
import org.json.JSONArray
import org.json.JSONException
import android.animation.ValueAnimator
import android.text.Editable
import android.text.TextWatcher


/**
 * Created by anarose on 11/20/17.
 */

class QuestionAdapter(internal var questions: Question, internal var mRecyclerView: RecyclerView) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {


    data class currentQuestion(val questionId: String, val answerId: String, val questoin_type: String)


    var cQ = currentQuestion("","","")


    private var mContext: Context? = null
    private val mLayoutInflater: LayoutInflater? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_questin, parent, false)
        mContext = parent.context

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        try {

            var choices = questions.questions.getJSONObject(position).getJSONArray("choices")
            var type = questions.questions.getJSONObject(position).getString("type")
            var questoin_id = questions.questions.getJSONObject(position).getString("id")




            if (questions.questionStatus(position) == Question.questionStatus.ACTIVE) {
                //  holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
                holder.question_parent_layout.visibility = View.VISIBLE
                holder.question_text.text = questions.questions.getJSONObject(position).getString("question")
                questionType(holder, type, choices, questoin_id)

            } else if (questions.questionStatus(position) == Question.questionStatus.COMPLETED) {

                changeHightWithAnimiation(holder.question_choices_layout)
                changeHightWithAnimiation(holder.question_text)
                holder.question_btn_next.isEnabled = false

            }
            holder.question_btn_next.setOnClickListener { listener ->
                onNextQuestion(holder, position)
            }

            setQuestionStatus(holder, questions.questionStatus(position))

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }



    internal fun changeHightWithAnimiation(layout: View) {
        val anim = ValueAnimator.ofInt(layout.getMeasuredHeight(), 0)
        anim.addUpdateListener { valueAnimator ->
            val anim = valueAnimator.animatedValue as Int
            val layoutParams = layout.getLayoutParams()
            layoutParams.height = anim
            layout.setLayoutParams(layoutParams)
        }
        anim.duration = 500
        anim.start()
    }

    internal fun setQuestionStatus(holder: ViewHolder, questionStatus: Question.questionStatus) {
        if (questionStatus == Question.questionStatus.ACTIVE) {
            holder.quesition_circle_indicator.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_active, R.color.colorAccent))
        } else if (questionStatus == Question.questionStatus.INACTIVE) {
            holder.quesition_circle_indicator.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_inactive, android.R.color.darker_gray))
        } else if (questionStatus == Question.questionStatus.COMPLETED) {
            holder.quesition_circle_indicator.setMarker(ContextCompat.getDrawable(mContext!!, R.drawable.ic_marker), ContextCompat.getColor(mContext!!, R.color.colorAccent))
        }
    }

    internal fun onNextQuestion(holder: ViewHolder, position: Int) {
        try {
            // questions.setStatus(position, Question.questionStatus.COMPLETED)
            // questions.setStatus(position + 1, Question.questionStatus.ACTIVE)
            //holder.question_btn_next.isEnabled = false
            holder.question_btn_next.startLoading()
            addAnswerAndCorrect(cQ.questionId, cQ.answerId.trim(), holder, position)
            // print(questions.questions)
            //     notifyDataSetChanged()
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    fun addAnswerAndCorrect(question_id: String, answer_id: String, holder: ViewHolder, position: Int) {

        val form = FormBody.Builder()
        form.add("student_id", "b1aVxQZcH9cFw8N8NHgizCY7Qgj1")
        form.add("question_id", question_id)
        form.add("answer_id", answer_id)
        form.add("question_type",cQ.questoin_type)

        OkhttpObservable
                .post("https://us-central1-questionsqate-9a3d7.cloudfunctions.net/correctAnswer", form)
                .subscribeOn(Schedulers.io())
                .map { response -> JsonParser().parse(response.body()!!.string()) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { response ->
                    if (response.asJsonObject.get("success").asBoolean.equals(true)) {
                        //Toast.makeText(this@MainActivity, response.toString(), Toast.LENGTH_SHORT).show()
                        System.out.println(response.toString())
                        if (response.asJsonObject.get("result").asBoolean) {
                            holder.question_btn_next.loadingSuccessful()
                        } else {
                            holder.question_btn_next.loadingFailed()
                        }
                        questions.setStatus(position, Question.questionStatus.COMPLETED)
                        if((position+1)<questions.questions.length()){
                            questions.setStatus(position + 1, Question.questionStatus.ACTIVE)
                        }
                        notifyDataSetChanged()
                    } else {

                        System.out.println(response.toString())

                        // Toast.makeText(this@MainActivity, response.asJsonObject.get("error").asString, Toast.LENGTH_SHORT).show()
                    }
                }.subscribe()

    }

    @Throws(JSONException::class)
    internal fun questionType(holder: ViewHolder, type: String, choices: JSONArray, questoin_id: String) {

        when (type) {
            "true_false" -> {
                val radiogroup = RadioGroup(mContext)

                val radioButtons = arrayOfNulls<RadioButton>(choices.length())

                for (i in 0 until choices.length()) {
                    radioButtons[i] = RadioButton(mContext)
                    radioButtons[i]!!.setText(choices.getJSONObject(i).getString("choice"))
                    radioButtons[i]!!.setOnClickListener {
                        cQ = currentQuestion(questoin_id, choices.getJSONObject(i).getInt("id").toString(),"true_false")
                    }
                    radiogroup.addView(radioButtons[i])
                }
                holder.question_choices_layout.addView(radiogroup)
            }
            "multiple_choice" -> {
                val answers = arrayOfNulls<CheckBox>(choices.length())
                var student_answer_set = HashSet<String>()

                for (i in 0 until choices.length()) {
                    answers[i] = CheckBox(mContext)
                    answers[i]!!.setText(choices.getJSONObject(i).getString("choice"))
                    answers[i]!!.setOnClickListener {
                        if(answers[i]!!.isChecked){
                            student_answer_set.add(choices.getJSONObject(i).getInt("id").toString())

                        }else{
                            student_answer_set.remove(choices.getJSONObject(i).getInt("id").toString())
                        }
                        cQ = currentQuestion(questoin_id, student_answer_set.toString(),"multiple_choice")
                        System.out.println(cQ.answerId)
                    }
                    holder.question_choices_layout.addView(answers[i])


                }
            }
            "one_word" -> {
                val editText = EditText(mContext)
                editText.width = holder.question_choices_layout.width
                editText.maxLines = 1
                editText.afterTextChanged {
                    cQ = currentQuestion(questoin_id, it,"one_word")
                }
                holder.question_choices_layout.addView(editText)


            }
        }
    }



    override fun getItemCount(): Int {
        return questions.questions.length()
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var question_text: TextView
        var question_choices_layout: LinearLayout
        var question_parent_layout: LinearLayout
        var quesition_circle_indicator: TimelineView
        var question_btn_next: LoadingButton
        var question_card_view: CardView

        init {
            question_text = itemView.findViewById(R.id.question_text)
            question_choices_layout = itemView.findViewById(R.id.question_choices_layout)
            question_parent_layout = itemView.findViewById(R.id.question_parent_layout)
            quesition_circle_indicator = itemView.findViewById(R.id.question_status_circle_indicator)
            question_card_view = itemView.findViewById(R.id.question_card_view)
            question_btn_next = itemView.findViewById(R.id.question_btn_next)

        }
    }
}


package com.questionqate.Adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.questionqate.Pojo.Question

import com.questionqate.R

import org.json.JSONArray
import org.json.JSONException

/**
 * Created by anarose on 12/10/17.
 */

class ViewLecturerQuestionsListAdapter(internal var questions: JSONArray, internal var activity: Activity) :
        RecyclerView.Adapter<ViewLecturerQuestionsListAdapter.ViewSubjectList_holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewLecturerQuestionsListAdapter.ViewSubjectList_holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.lecturer_view_questions, parent, false)

        println(questions)
        return ViewSubjectList_holder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewLecturerQuestionsListAdapter.ViewSubjectList_holder, position: Int) {

        try {

        var type = questions.getJSONObject(position).getString("type")
            println(" from adapter "+type)
            if(!type.equals("end")){
                holder.lect_question_name.text = "Question: "+questions.getJSONObject(position).getString("question")
                var choices = questions.getJSONObject(position).getJSONArray("choices")
                questionType(holder,type, choices)

            }


        } catch (e: JSONException) { e.printStackTrace() }


    }

    @Throws(JSONException::class)
    internal fun questionType(holder: ViewSubjectList_holder,type: String, choices: JSONArray) {

        when (type) {
            "true_false" -> {

                val radiogroup = RadioGroup(activity)
                val radioButtons = arrayOfNulls<RadioButton>(choices.length())

                for (i in 0 until choices.length()) {
                    radioButtons[i] = RadioButton(activity)
                    radioButtons[i]!!.setText(choices.getJSONObject(i).getString("choice"))
                    radiogroup.addView(radioButtons[i])
                }
                holder.lect_choices.addView(radiogroup)
            }
            "multiple_choice" -> {

                val answers = arrayOfNulls<CheckBox>(choices.length())

                for (i in 0 until choices.length()) {
                    answers[i] = CheckBox(activity)
                    answers[i]!!.setText(choices.getJSONObject(i).getString("choice"))

                    holder.lect_choices.addView(answers[i])

                }
            }
            "one_word" -> {

                val answers = arrayOfNulls<TextView>(choices.length())

                for (i in 0 until choices.length()) {
                    answers[i] = TextView(activity)
                    answers[i]!!.setText(choices.getJSONObject(i).getString("choice"))
                    answers[i]!!.setTextSize(15F)
                    holder.lect_choices.addView(answers[i])
                }

            }
        }
    }


    override fun getItemCount(): Int {
        return questions.length()-1
    }

    inner class ViewSubjectList_holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var lect_choices: LinearLayout
        var lect_question_card: CardView
        var lect_question_name: TextView

        init {
            lect_choices = itemView.findViewById(R.id.lect_choices)
            lect_question_card = itemView.findViewById(R.id.lect_question_card)
            lect_question_name= itemView.findViewById(R.id.lect_question_name)

        }

    }


}

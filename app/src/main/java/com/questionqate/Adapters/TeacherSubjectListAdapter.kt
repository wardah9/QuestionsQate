package com.questionqate.Adapters

import android.app.Activity
import android.graphics.Color.WHITE
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.gson.JsonArray
import com.questionqate.Pojo.QuestionHelper

import com.questionqate.Pojo.ViewState
import com.questionqate.Pojo.ViewState.Subject_CardView_States
import com.questionqate.R
import com.questionqate.Utilties.EventBus
import com.squareup.picasso.Picasso
import java.io.IOException

/**
 * Created by anarose on 11/14/17.
 */

class TeacherSubjectListAdapter(internal var subjects: JsonArray, internal var context: Activity, internal var sendValues: Boolean) : RecyclerView.Adapter<TeacherSubjectListAdapter.SubjectList_holder>() {
    var status = arrayListOf<Subject_CardView_States>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectList_holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_subjects_list, parent, false)

        subjects.forEach {
            status.add(ViewState.Subject_CardView_States(false))
        }

        return SubjectList_holder(v)
    }

    override fun onBindViewHolder(holder: SubjectList_holder, position: Int) {

        holder.subject_name_view.text = subjects.get(position).asJsonObject.get("name").asString
        Picasso.with(context).load(subjects.get(position).asJsonObject.get("imageURL").asString)
                .into(holder.subject_image_view)

//        EventBus.notifyLecturerSubjectChange(subjects.get(position).asJsonObject.get("name").asString)

        if (!status[position].isPressed) {
            holder.border.setBackgroundColor(WHITE)
        }

        holder.subjet_card.setOnClickListener {

            if (sendValues) {
                EventBus.notifyLecturerSubjectChange(subjects.get(position).asJsonObject.get("name").asString)
            }
            holder.border.setBackgroundResource(R.drawable.border)
            setStatus(position)
            QuestionHelper.subject_name = subjects.get(position).asJsonObject.get("name").asString
            notifyDataSetChanged()
        }

    }

    private fun setStatus(position: Int) {
        status.forEach { e ->
            e.isPressed = false
        }
        status[position].isPressed = true
    }

    override fun getItemCount(): Int {
        return subjects.size()
    }

    inner class SubjectList_holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var subject_image_view: ImageView = itemView.findViewById(R.id.subject_image_view)
        internal var subject_name_view: TextView = itemView.findViewById(R.id.subject_name_view)
        internal var subjet_card: CardView = itemView.findViewById(R.id.subjet_card)
        internal var border: RelativeLayout = itemView.findViewById(R.id.card_subject_border)

    }
}

package com.questionqate.Adapters

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.JsonArray

import com.questionqate.Activities.LevelsActivity
import com.questionqate.R
import com.squareup.picasso.Picasso

import org.json.JSONArray

/**
 * Created by anarose on 11/14/17.
 */

class TeacherSubjectListAdapter(internal var subjects: JsonArray, internal var context:Activity) : RecyclerView.Adapter<TeacherSubjectListAdapter.SubjectList_holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectList_holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_subjects_list, parent, false)
        return SubjectList_holder(v)
    }

    override fun onBindViewHolder(holder: SubjectList_holder, position: Int) {

        holder.subject_name_view.text = subjects.get(position).asJsonObject.get("name").asString
        Picasso.with(context).load(subjects.get(position).asJsonObject.get("imageURL").asString).into(  holder.subject_image_view)


    }

    override fun getItemCount(): Int {
        return subjects.size()
    }

    inner class SubjectList_holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var subject_image_view: ImageView
        internal var subject_name_view: TextView
        internal var subjet_card: CardView

        init {

            subject_image_view = itemView.findViewById(R.id.subject_image_view)
            subject_name_view = itemView.findViewById(R.id.subject_name_view)
            subjet_card = itemView.findViewById(R.id.subjet_card)
        }
    }
}

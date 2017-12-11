package com.questionqate.Adapters

import android.app.Activity
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.questionqate.Pojo.ViewState
import com.questionqate.R
import com.questionqate.Utilties.EventBus

/**
 * Created by anarose on 11/14/17.
 */

class TeacherLevel_viewListAdapter(internal var context: Activity) : RecyclerView.Adapter<TeacherLevel_viewListAdapter.Level_List_holder>() {
    var status =  arrayListOf<ViewState.Subject_CardView_States>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Level_List_holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_subjects_list, parent, false)
        return Level_List_holder(v)
    }

    override fun onBindViewHolder(holder: Level_List_holder, position: Int) {

        status.add(ViewState.Subject_CardView_States(false))
        holder.subject_image_view.setImageResource(R.mipmap.ic_launcher)
        holder.subject_name_view.visibility = View.GONE

        if(!status[position].isPressed){
            holder.border.setBackgroundColor(Color.WHITE)
        }

        holder.subjet_card.setOnClickListener {
            holder.border.setBackgroundResource(R.drawable.border)
            setStatus(position)
            EventBus.notifyLecturerlevelChange(position)
            notifyDataSetChanged()
        }

    }

    private fun setStatus(position: Int) {
        status.forEach { e->
            e.isPressed = false
        }
        status[position].isPressed = true
    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class Level_List_holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var subject_image_view: ImageView = itemView.findViewById(R.id.subject_image_view)
        internal var subject_name_view: TextView = itemView.findViewById(R.id.subject_name_view)
        internal var subjet_card: CardView = itemView.findViewById(R.id.subjet_card)
        internal var  border: RelativeLayout = itemView.findViewById(R.id.card_subject_border)

    }
}
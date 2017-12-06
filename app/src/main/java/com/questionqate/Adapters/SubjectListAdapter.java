package com.questionqate.Adapters;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.questionqate.Activities.LevelsActivity;
import com.questionqate.R;
import com.squareup.picasso.Picasso;

/**
 * Created by anarose on 11/14/17.
 */

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.SubjectList_holder> {

    Activity context1;

    public SubjectListAdapter(Activity context) {

        this.context1 = context;
    }

    @Override
    public SubjectList_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_subject_list_stu, parent, false);
        return new SubjectList_holder(v);
    }

    @Override
    public void onBindViewHolder(SubjectList_holder holder, final int position) {

//        holder.subject_name_view.setText(subjects.get(position).asJsonObject.get("name").asString)
//        Picasso.with(context1).load(subjects.get(position).asJsonObject.get("imageURL").asString)
//                .into(holder.subject_image_view);

        holder.subjet_card.setOnClickListener(v -> {
            if (position == 0){

                Intent intent=  new Intent(context1, LevelsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context1.startActivity(intent);
                context1.finish();
            }


        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class SubjectList_holder extends RecyclerView.ViewHolder {

        ImageView subject_image_view;
        TextView subject_name_view;
        CardView subjet_card;

        public SubjectList_holder(View itemView) {
            super(itemView);

            subject_image_view = itemView.findViewById(R.id.subject_image_view);
            subject_name_view = itemView.findViewById(R.id.subject_name_view);
            subjet_card = itemView.findViewById(R.id.subjet_card);
        }
    }
}

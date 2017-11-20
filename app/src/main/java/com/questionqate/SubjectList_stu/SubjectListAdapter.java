package com.questionqate.SubjectList_stu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.questionqate.LevelsList.LevelsActivity;
import com.questionqate.R;

/**
 * Created by anarose on 11/14/17.
 */

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.SubjectList_holder> {

    Context context1;

    public SubjectListAdapter(Context context) {

        this.context1 = context;
    }

    @Override
    public SubjectList_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_subjects_list, parent, false);
        return new SubjectList_holder(v);
    }

    @Override
    public void onBindViewHolder(SubjectList_holder holder, final int position) {

        holder.subjet_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0)
                    context1.startActivity(new Intent(context1, LevelsActivity.class));
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

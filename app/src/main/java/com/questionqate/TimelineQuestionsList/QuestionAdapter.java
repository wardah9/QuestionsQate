package com.questionqate.TimelineQuestionsList;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.questionqate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anarose on 11/20/17.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    JSONArray questions;
    private List<TimeLineModel> mFeedList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public QuestionAdapter(JSONArray questoin) {
        this.questions = questoin;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_questin, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


            try {
                if(position==0){
                    holder.question_card_view.setVisibility(View.GONE);
                }
                else{
                    holder.question_text.setText(questions.getJSONObject(position).getString("question"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }

    @Override
    public int getItemCount() {
        return questions.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView question_text;
        CardView question_card_view;


        public ViewHolder(View itemView) {
            super(itemView);
            question_text = itemView.findViewById(R.id.question_text);

            question_card_view=itemView.findViewById(R.id.question_card_view);

        }
    }
}


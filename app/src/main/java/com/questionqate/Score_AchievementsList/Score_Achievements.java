package com.questionqate.Score_AchievementsList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.questionqate.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Score_Achievements extends AppCompatActivity {

    RecyclerView achievements_score_list;
    AndroidNetworking ndroidNetworking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score__achievements);

        achievements_score_list = findViewById(R.id.achievements_score_list);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        achievements_score_list.setLayoutManager(mLayoutManager);
        achievements_score_list.setItemAnimator(new DefaultItemAnimator());
//        achievements_score_list.setHasFixedSize(true);


        ndroidNetworking.post("https://us-central1-questionsqate-9a3d7.cloudfunctions.net/getAchievements")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        achievements_score_list.setAdapter(new Score_AchievementsAdapter(Score_Achievements.this,response));
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Achievement Error:",anError.getErrorDetail()+"\n"+anError.getErrorBody());
                    }
                });

    }

//    private LinearLayoutManager getLinearLayoutManager() {
//        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//    }

}

class Score_AchievementsAdapter extends RecyclerView.Adapter<Score_AchievementsAdapter.Score_Achievement_Holder> {

    JSONArray score_achievementsData;
    Context context;

    public Score_AchievementsAdapter(Context context, JSONArray score_achievementsData) {
        this.context = context;
        this.score_achievementsData = score_achievementsData;
    }

    @Override
    public Score_Achievement_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_score_achievements, parent, false);
        return new Score_Achievement_Holder(v);
    }

    @Override
    public void onBindViewHolder(Score_Achievement_Holder holder, int position) {

        try {
            if (score_achievementsData.getJSONObject(position)!= null) {

                String low = "https://firebasestorage.googleapis.com/v0/b/questionsqate-9a3d7.appspot.com/o/trophy%20(4).png?alt=media&token=5286d9a8-9d7b-4079-a251-fb55ed944c55";
                String medium = "https://firebasestorage.googleapis.com/v0/b/questionsqate-9a3d7.appspot.com/o/trophy%20(6).png?alt=media&token=bda91fab-9285-4541-bd28-0bc3360b6bfc";
                String high = "https://firebasestorage.googleapis.com/v0/b/questionsqate-9a3d7.appspot.com/o/trophy%20(2).png?alt=media&token=45dabb10-b0d9-4e98-9438-b34d3e183958";

                    String level = score_achievementsData.getJSONObject(position).getString("level_name");
                    switch (level) {
                        case "low":
                            holder.ach_level_name.setText( "LEVEL :  "+level);
                            Picasso.with(context).load(low).into(holder.ach_img);
                            break;
                        case "medium":
                            holder.ach_level_name.setText( "LEVEL :  "+level );
                            Picasso.with(context).load(medium).resize(100, 100).into(holder.ach_img);
                            break;
                        case "high":
                            holder.ach_level_name.setText( "LEVEL :  "+level);
                            Picasso.with(context).load(high).resize(100, 100).into(holder.ach_img);
                            break;
                    }
                    int score = score_achievementsData.getJSONObject(position).getInt("score");
                    holder.ach_stu_score.setText("SCORE : "+score + "/ 3");


                    holder.ach_share.setOnClickListener(view -> {

                        Uri bmpUri = getLocalBitmapUri(holder.ach_img);
                        if (bmpUri != null) {
                            Intent share = new Intent(Intent.ACTION_SEND);
                            share.setType("image/*");
                            share.putExtra(Intent.EXTRA_STREAM, bmpUri);
                            share.putExtra(Intent.EXTRA_TEXT, "I got something cool! , i have maximum score On " + level + " level \n check it out from Questions Gate applicatio ..");
                            context.startActivity(Intent.createChooser(share, "Share Your Achievement !"));
                        }
                    });

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @Override
    public int getItemCount() {
        return score_achievementsData.length();
    }

    public class Score_Achievement_Holder extends RecyclerView.ViewHolder {

        ImageView ach_img;
        ImageView ach_share;
        TextView ach_level_name;
        TextView ach_stu_score;

        public Score_Achievement_Holder(View itemView) {
            super(itemView);

            ach_img = itemView.findViewById(R.id.ach_img);
            ach_share = itemView.findViewById(R.id.ach_share);
            ach_level_name = itemView.findViewById(R.id.ach_level_name);
            ach_stu_score = itemView.findViewById(R.id.ach_stu_score);
        }
    }
}

package com.questionqate.StudentProfile;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.questionqate.R;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    String url_img = "https://firebasestorage.googleapis.com/v0/b/questionsqate-9a3d7.appspot.com/o/girl.png?alt=media&token=f9831356-447e-4de9-b5cf-7d45b169c056";
    ImageView profile_image;
    TextView profile_name;
    TextView profile_mail;
    TextView profile_phone;
    EditText profile_name_edt;
    EditText profile_mail_edt;
    EditText profile_phone_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflator != null) {
                View v = inflator.inflate(R.layout.card_view_questin, null);
                actionBar.setCustomView(v);
            }
        }
        profile_image = findViewById(R.id.profile_image);
        Picasso.with(this).load(url_img).into(profile_image);

        profile_phone = findViewById(R.id.profile_phone);
        profile_mail = findViewById(R.id.profile_mail);
        profile_name = findViewById(R.id.profile_name);

        profile_name_edt = findViewById(R.id.profile_name_edt);
        profile_mail_edt = findViewById(R.id.profile_mail_edt);
        profile_phone_edt = findViewById(R.id.profile_phone_edt);


    }
}

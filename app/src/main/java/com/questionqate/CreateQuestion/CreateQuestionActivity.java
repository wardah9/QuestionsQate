package com.questionqate.CreateQuestion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.questionqate.R;

public class CreateQuestionActivity extends AppCompatActivity implements View.OnClickListener {

    EditText question_edt;
    LinearLayout answer_content;

    Button medium, low, high;
    ImageView type1,type2,type3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        medium = findViewById(R.id.medium);
        low = findViewById(R.id.low);
        high = findViewById(R.id.high);
        type1 = findViewById(R.id.type1);
        type2 = findViewById(R.id.type2);
        type3 = findViewById(R.id.type3);


        question_edt = findViewById(R.id.question_edt);
        answer_content = findViewById(R.id.answer_content);

        medium.setOnClickListener(this);
        low.setOnClickListener(this);
        high.setOnClickListener(this);
        type1.setOnClickListener(this);
        type2.setOnClickListener(this);
        type3.setOnClickListener(this);

    }

    public void AddToFirebase(View view) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.type1:
                question_edt.setVisibility(View.VISIBLE);
                answer_content.setVisibility(View.VISIBLE);
                break;

            case R.id.type2:
                question_edt.setVisibility(View.VISIBLE);
                answer_content.setVisibility(View.GONE);
                break;

            case R.id.type3:
                question_edt.setVisibility(View.VISIBLE);
                answer_content.setVisibility(View.GONE);
                break;

            case R.id.medium: {
                medium.setBackgroundResource(R.drawable.btn_clicked);
                low.setBackgroundResource(R.drawable.corner);
                high.setBackgroundResource(R.drawable.corner);
                break;
            }

            case R.id.low:
                medium.setBackgroundResource(R.drawable.corner);
                low.setBackgroundResource(R.drawable.btn_clicked);
                high.setBackgroundResource(R.drawable.corner);
                break;
            case R.id.high:
                medium.setBackgroundResource(R.drawable.corner);
                low.setBackgroundResource(R.drawable.corner);
                high.setBackgroundResource(R.drawable.btn_clicked);
                break;
        }
    }
}

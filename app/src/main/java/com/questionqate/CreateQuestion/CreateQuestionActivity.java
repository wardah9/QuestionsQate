package com.questionqate.CreateQuestion;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.questionqate.Adapters.TeacherSubjectListAdapter;
import com.questionqate.Pojo.Teacher;
import com.questionqate.R;

public class CreateQuestionActivity extends AppCompatActivity implements View.OnClickListener {

    EditText question_edt, shortAnswer, noOfAnswers;
    LinearLayout answer_content, what, answers;

    Button medium, low, high, add_answers_list;
    ImageView type1, type2, type3;

    RecyclerView recyclerView;

    LinearLayout subjectlist, levelslist, questionstypelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        recyclerView = findViewById(R.id.create_question_subjects_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (Teacher.INSTANCE.getCurrentTeacher().getSubjects() != null) {
            recyclerView.setAdapter(new TeacherSubjectListAdapter(Teacher.INSTANCE.getCurrentTeacher().getSubjects(), this));
        } else
            Toast.makeText(this, "no subjects ", Toast.LENGTH_SHORT).show();

        medium = findViewById(R.id.medium);
        low = findViewById(R.id.low);
        high = findViewById(R.id.high);
        type1 = findViewById(R.id.type1);
        type2 = findViewById(R.id.type2);
        type3 = findViewById(R.id.type3);
        question_edt = findViewById(R.id.question_edt);
        shortAnswer = findViewById(R.id.shortAnswer);
        answer_content = findViewById(R.id.answer_content);
        answers = findViewById(R.id.answers);
        noOfAnswers = findViewById(R.id.noOfAnswers);
        add_answers_list = findViewById(R.id.add_answers_list);
        what = findViewById(R.id.what);
        subjectlist = findViewById(R.id.subjectlist);
        levelslist = findViewById(R.id.levelslist);
        questionstypelist = findViewById(R.id.questionstypelist);

        medium.setOnClickListener(this);
        low.setOnClickListener(this);
        high.setOnClickListener(this);
        type1.setOnClickListener(this);
        type2.setOnClickListener(this);
        type3.setOnClickListener(this);
        add_answers_list.setOnClickListener(this);

    }

    public void AddToFirebase(View view) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.type1:
                question_edt.setVisibility(View.VISIBLE);
                answer_content.setVisibility(View.VISIBLE);
                what.setVisibility(View.GONE);
                shortAnswer.setVisibility(View.GONE);
                answers.setVisibility(View.GONE);
                subjectlist.setVisibility(View.GONE);
                levelslist.setVisibility(View.GONE);
                break;

            case R.id.type2:
                question_edt.setVisibility(View.VISIBLE);
                answer_content.setVisibility(View.GONE);
                what.setVisibility(View.VISIBLE);
                shortAnswer.setVisibility(View.GONE);
                answers.setVisibility(View.GONE);
                subjectlist.setVisibility(View.GONE);
                levelslist.setVisibility(View.GONE);
                break;

            case R.id.type3:
                question_edt.setVisibility(View.VISIBLE);
                answer_content.setVisibility(View.GONE);
                what.setVisibility(View.GONE);
                shortAnswer.setVisibility(View.VISIBLE);
                answers.setVisibility(View.GONE);
                subjectlist.setVisibility(View.GONE);
                levelslist.setVisibility(View.GONE);
                break;

            case R.id.medium:
                medium.setBackgroundResource(R.drawable.btn_clicked);
                low.setBackgroundResource(R.drawable.corner);
                high.setBackgroundResource(R.drawable.corner);
                subjectlist.setVisibility(View.GONE);
                break;

            case R.id.low:
                medium.setBackgroundResource(R.drawable.corner);
                low.setBackgroundResource(R.drawable.btn_clicked);
                high.setBackgroundResource(R.drawable.corner);
                subjectlist.setVisibility(View.GONE);
                break;
            case R.id.high:
                medium.setBackgroundResource(R.drawable.corner);
                low.setBackgroundResource(R.drawable.corner);
                high.setBackgroundResource(R.drawable.btn_clicked);
                subjectlist.setVisibility(View.GONE);
                break;
            case R.id.add_answers_list:
                addAnswersList();
                answers.setVisibility(View.VISIBLE);
                questionstypelist.setVisibility(View.GONE);
                break;
        }
    }

    private void addAnswersList() {

        int count = Integer.parseInt(noOfAnswers.getText().toString());
        answers.removeAllViews();

        LinearLayout temp0 = new LinearLayout(this);
        temp0.setOrientation(LinearLayout.HORIZONTAL);
        temp0.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        TextView title1 = new TextView(this);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        textParams.setMargins(90, 0, 0, 0);
        title1.setLayoutParams(textParams);

        title1.setText("answers text");
        temp0.addView(title1);

        TextView title2 = new TextView(this);
        title2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        title2.setText("choose correct answer");
        temp0.addView(title2);

        answers.addView(temp0);

        for (int i = 0; i < count; i++) {

            LinearLayout temp = new LinearLayout(this);
            temp.setOrientation(LinearLayout.HORIZONTAL);
            temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            EditText editText = new EditText(this);
            LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(500, LinearLayout.LayoutParams.WRAP_CONTENT);
            editParams.setMargins(50, 0, 0, 0);
            editText.setLayoutParams(editParams);
            editText.setHint("write answer ");
            temp.addView(editText);

            CheckBox checkbox = new CheckBox(this);
            checkbox.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            temp.addView(checkbox);

            answers.addView(temp);

        }
    }
}

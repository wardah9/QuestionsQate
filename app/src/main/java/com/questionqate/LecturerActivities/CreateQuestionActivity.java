package com.questionqate.LecturerActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.questionqate.Adapters.TeacherSubjectListAdapter;
import com.questionqate.Dialog.LoadingDialog;
import com.questionqate.Interface.Exceptions;
import com.questionqate.Pojo.QuestionHelper;
import com.questionqate.Pojo.Teacher;
import com.questionqate.R;
import com.questionqate.Utilties.EventBus;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;

import css.fingerprint.Networking.OkhttpObservable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;

public class CreateQuestionActivity extends AppCompatActivity implements View.OnClickListener, Exceptions {

    EditText question_edt, shortAnswer, noOfAnswers;
    LinearLayout answer_content, what, answers;
    Button medium, low, high, add_answers_list;
    ImageView type1, type2, type3;
    RecyclerView recyclerView;
    LinearLayout subjectlist, levelslist, questionstypelist;
    RadioGroup true_false;

    LoadingDialog dialog;

    QuestionHelper.currentQuestion cr = new QuestionHelper.currentQuestion("", "", new JSONArray(), new JSONArray());
    JSONArray jsonArrayChoices = new JSONArray();
    JSONArray jsonArrayCorrectChoices = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        EventBus.INSTANCE.addExceptionsListener(this);

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
        true_false = findViewById(R.id.true_false);

        medium.setOnClickListener(this);
        low.setOnClickListener(this);
        high.setOnClickListener(this);
        type1.setOnClickListener(this);
        type2.setOnClickListener(this);
        type3.setOnClickListener(this);
        add_answers_list.setOnClickListener(this);
        true_false.setOnCheckedChangeListener((radioGroup, i) -> {

            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.two:
                    try {
                        cr.setCorrectAnswerid(new JSONArray().put(new JSONObject().put("iscorrect", 1)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.one:
                    try {
                        cr.setCorrectAnswerid(new JSONArray().put(new JSONObject().put("iscorrect", 0)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
            }

        });

    }

    public void AddToFirebase(View view) {
        cr.setQuestion(question_edt.getText().toString());
        switch (cr.getQuestion_type()) {
            case "true_false":
                try {
                    JSONArray jsonChoices = new JSONArray();
                    JSONObject jsontrue = new JSONObject();
                    jsontrue.put("choice", "true");
                    jsontrue.put("id", 0);
                    jsonChoices.put(jsontrue);
                    JSONObject jsonfalse = new JSONObject();
                    jsonfalse.put("choice", "false");
                    jsonfalse.put("id", 1);
                    jsonChoices.put(jsonfalse);
                    cr.setChoice(jsonChoices);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //QuestionHelper.INSTANCE.toApi(cr);
                toApi(cr);


                break;
            case "multiple_choice":
                cr.setChoice(jsonArrayChoices);
                cr.setCorrectAnswerid(jsonArrayCorrectChoices);
                //QuestionHelper.INSTANCE.toApi(cr);
                toApi(cr);
                break;
            case "one_word":
                try {
                    JSONArray jsonArrayOneword = new JSONArray();
                    JSONObject jsonOneword = new JSONObject();
                    jsonOneword.put("choice", shortAnswer.getText().toString());
                    jsonOneword.put("id", 0);
                    jsonArrayOneword.put(jsonOneword);
                    cr.setChoice(jsonArrayOneword);
                    cr.setCorrectAnswerid(new JSONArray().put(new JSONObject().put("iscorrect", shortAnswer.getText().toString())));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //QuestionHelper.INSTANCE.toApi(cr);
                toApi(cr);
                break;
        }


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
                cr.setQuestion_type("true_false");

                break;

            case R.id.type2:
                question_edt.setVisibility(View.VISIBLE);
                answer_content.setVisibility(View.GONE);
                what.setVisibility(View.VISIBLE);
                shortAnswer.setVisibility(View.GONE);
                answers.setVisibility(View.GONE);
                subjectlist.setVisibility(View.GONE);
                levelslist.setVisibility(View.GONE);
                cr.setQuestion_type("multiple_choice");

                break;

            case R.id.type3:
                question_edt.setVisibility(View.VISIBLE);
                answer_content.setVisibility(View.GONE);
                what.setVisibility(View.GONE);
                shortAnswer.setVisibility(View.VISIBLE);
                answers.setVisibility(View.GONE);
                subjectlist.setVisibility(View.GONE);
                levelslist.setVisibility(View.GONE);
                cr.setQuestion_type("one_word");

                break;

            case R.id.medium:
                medium.setBackgroundResource(R.drawable.btn_clicked);
                low.setBackgroundResource(R.drawable.corner);
                high.setBackgroundResource(R.drawable.corner);
                subjectlist.setVisibility(View.GONE);
                QuestionHelper.INSTANCE.setLevel_id(2);
                break;

            case R.id.low:
                medium.setBackgroundResource(R.drawable.corner);
                low.setBackgroundResource(R.drawable.btn_clicked);
                high.setBackgroundResource(R.drawable.corner);
                subjectlist.setVisibility(View.GONE);
                QuestionHelper.INSTANCE.setLevel_id(1);
                break;
            case R.id.high:
                medium.setBackgroundResource(R.drawable.corner);
                low.setBackgroundResource(R.drawable.corner);
                high.setBackgroundResource(R.drawable.btn_clicked);
                subjectlist.setVisibility(View.GONE);
                QuestionHelper.INSTANCE.setLevel_id(3);
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

            int finalI = i;
            try {
                jsonArrayChoices.put(finalI, new JSONObject().put("choice", "").put("id", finalI));
                jsonArrayCorrectChoices.put(finalI, new JSONObject().put("iscorrect", false));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        jsonArrayChoices.put(finalI, new JSONObject().put("choice", editable.toString()).put("id", finalI));
                    } catch (JSONException e) {


                    }

                }
            });


            CheckBox checkbox = new CheckBox(this);
            checkbox.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    try {
                        jsonArrayCorrectChoices.put(finalI, new JSONObject().put("iscorrect", finalI));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            temp.addView(checkbox);

            answers.addView(temp);

        }
    }


    void toApi(QuestionHelper.currentQuestion currentQuestion) {

        dialog = new LoadingDialog(this, "adding Question...");
        dialog.setCancelable(false);
        dialog.doAction("OK", view -> {
            dialog.dismiss();
            startActivity(new Intent(CreateQuestionActivity.this,LecturerHome.class));
            finish();
        });

        dialog.show();
        FormBody.Builder toAPi = new FormBody.Builder();
        toAPi.add("subject_name", QuestionHelper.INSTANCE.getSubject_name());
        toAPi.add("level_id", String.valueOf(QuestionHelper.INSTANCE.getLevel_id()));
        toAPi.add("question", currentQuestion.getQuestion());
        toAPi.add("question_type", currentQuestion.getQuestion_type());
        toAPi.add("choices", String.valueOf(currentQuestion.getChoice()));
        toAPi.add("correct_answers", String.valueOf(currentQuestion.getCorrectAnswerid()));
        System.out.println("subject_name " + QuestionHelper.INSTANCE.getSubject_name());
        System.out.println("level_id " + QuestionHelper.INSTANCE.getLevel_id());
        System.out.println("question " + currentQuestion.getQuestion());
        System.out.println("question_type " + currentQuestion.getQuestion_type());
        System.out.println("choices " + String.valueOf(currentQuestion.getChoice()));
        System.out.println("correct_answers " + String.valueOf(currentQuestion.getCorrectAnswerid()));

        OkhttpObservable.INSTANCE
                .post("https://us-central1-questionsqate-9a3d7.cloudfunctions.net/addQuestion", toAPi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> error.printStackTrace())
                .doOnNext(e-> dialog.hideProgress())
                .doOnNext(e-> dialog.setResultText("Question added successfully :)"))
                .doOnNext(e -> System.out.println("addd question " + e))
                .doOnNext(e-> dialog.showActionButton())
                .subscribe();

    }

    @Override
    public void onNetworkException(@NotNull String e) {

        if (dialog.isShowing()){
            dialog.dismiss();
        }

    }
}

package com.questionqate.Activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.questionqate.Adapters.QuestionAdapter;
import com.questionqate.Interface.StrikeTimeInterface;
import com.questionqate.Pojo.QuestionList;
import com.questionqate.R;
import com.questionqate.Utilties.EventBus;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class QuestionsMainView extends AppCompatActivity implements StrikeTimeInterface {

    private AndroidNetworking ndroidNetworking;

    private RecyclerView mRecyclerView;
    private Orientation mOrientation = Orientation.VERTICAL;
    private int user_strike_time=0;
    private int strike_counter=0;


    public enum Orientation {
        VERTICAL
    }

    public enum OrderStatus {

        COMPLETED,
        ACTIVE,
        INACTIVE;

    }

    Chronometer chronometer;
    TextView levek_strike_text,user_strike_counter;
    NumberFormat f = new DecimalFormat("00");
    int minCounter=0;
    int currentCOunter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_list);

        EventBus.INSTANCE.addStrikeTimeListener(this);

        chronometer=findViewById(R.id.chrono_counter);
        levek_strike_text=findViewById(R.id.counter_textview);
        user_strike_counter=findViewById(R.id.user_counter_textview);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

     //   System.out.print(QuestionList.getInstance().questionList.get(0).getQuestions());
        currentCOunter = QuestionList.getInstance().getQuestionList().get(0).getStrikeTime();
        String textCounter="Level Strike Time "+"00:"+f.format(currentCOunter);
        levek_strike_text.setText(textCounter);

        String textCounter2="Your Strike Time "+"00:00";
        user_strike_counter.setText(textCounter2);


        mRecyclerView.setAdapter(new QuestionAdapter(QuestionList.getInstance().getQuestionList().get(0)));
         //chronometer.setBase(0);

        System.out.println("strike time = "+QuestionList.getInstance().getQuestionList().get(0).getStrikeTime());
        chronometer.start();

//        chronometer.setOnChronometerTickListener(chronometer -> {
//           // String textCounter="Your Strike Time"+"00:"+f.format(currentCOunter) +"s";
//            if(currentCOunter>minCounter){
//                currentCOunter--;
//            }
//            System.out.print(textCounter);
//            choronometer_text.setText(textCounter);
//
//        });

    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    void ToastAchivement(){

        LayoutInflater li = getLayoutInflater();
        //Getting the View object as defined in the customtoast.xml file
        View layout = li.inflate(R.layout.achivement,
                findViewById(R.id.achievement_layout));
        TextView level = (TextView)layout.findViewById(R.id.achivement_level_text);


        //Creating the Toast object
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setView(layout);//setting the view of custom toast layout
        toast.show();
    }
    void AchivementNotification(){
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(),new Intent(), 0);

        Uri uriSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Achievement")
                .setContentText("Congratulations,you got new record on level")
                .setSmallIcon(R.drawable.trophy)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.trophy))
                .setSound(uriSound)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        assert manager != null;
        manager.notify(1, notification);
    }


    @Override
    public void onStrike(int time) {
        user_strike_time = user_strike_time + time;
        String strike="Your Strike Time "+"00:"+f.format(user_strike_time);
        user_strike_counter.setText(strike);
        strike_counter++;
        if(strike_counter==3){
            ToastAchivement();
            AchivementNotification();
        }

    }


    @Override
    public void RemoveStrike() {
        if(strike_counter!=3){
            // u reached to achivement
            user_strike_time = 0;
            String strike="Your Strike Time "+"00:"+f.format(user_strike_time);
            user_strike_counter.setText(strike);
            strike_counter=0;
        }

    }

}



package com.questionqate.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding2.view.RxView;
import com.questionqate.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SplashScreen extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        RelativeLayout relative = findViewById(R.id.relative);
        RxView.attaches(relative)
                .debounce(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(e-> startActivity(new Intent(SplashScreen.this,MainActivity.class))).subscribe();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative:
//                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                break;
        }
    }
}

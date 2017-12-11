package com.questionqate.Utilties;

import android.app.Application;

import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by CodeCrazy on 12/10/17.
 */

public class Config extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseMessaging.getInstance().subscribeToTopic("newQuestions");
    }
}

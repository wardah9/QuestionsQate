package com.questionqate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

/**
 * Created by anarose on 11/24/17.
 */

public class AlertAchievement extends AlertDialog.Builder {

    public AlertAchievement(@NonNull Context context) {
        super(context);
    }

    public AlertAchievement(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
}

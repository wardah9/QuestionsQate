package com.questionqate.Utilties;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by CodeCrazy on 11/25/17.
 */

public class LoadingDialog {
    public com.questionqate.Dialog.LoadingDialog init(Activity context, String loading_text) {
        com.questionqate.Dialog.LoadingDialog dialog=new com.questionqate.Dialog.LoadingDialog(context,loading_text);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        //  wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
      return dialog;
    }

}

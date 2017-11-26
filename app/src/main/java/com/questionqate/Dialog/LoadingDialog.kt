package com.questionqate.Dialog

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import com.questionqate.Interface.Exceptions
import com.questionqate.R
import com.questionqate.Utilties.EventBus
import kotlinx.android.synthetic.main.dialogloading.*

/**
 * Created by CodeCrazy on 11/25/17.
 */
class LoadingDialog(context: Activity,loadingtext: String) : Dialog(context) , Exceptions {
    override fun onNetworkException(e: String) {
        var result = "Internet Connection Error"
        loading_dialog_text.setText(result)

    }
    init {

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialogloading)

        EventBus.addExceptionsListener(this)
        loading_dialog_text.text = loadingtext
    }

    fun doAction(action: View.OnClickListener) {

        loading_dialog_action_btn.setOnClickListener(action)
    }
    fun getActionButton(): Button? {
       return loading_dialog_action_btn
    }

    fun setResultText(result_text: String) {
         loading_dialog_text.text = result_text
    }

    fun hideProgress(){
        loading_progress_bar.visibility = View.GONE
    }

//    fun ProgressBar(): ProgressBar? {
//        return loading_progress_bar
//    }
}
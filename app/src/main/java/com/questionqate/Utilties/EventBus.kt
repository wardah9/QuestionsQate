package com.questionqate.Utilties

import com.questionqate.Interface.StrikeTimeInterface
import com.questionqate.Interface.Exceptions
import java.util.ArrayList

/**
 * Created by CodeCrazy on 6/18/17.
 */

object EventBus {


    internal var ExceptionInterfaceList: MutableList<Exceptions> = ArrayList()
    internal var StrikeTime: MutableList<StrikeTimeInterface> = ArrayList()


    fun addExceptionsListener (listener: Exceptions){
        ExceptionInterfaceList.add(listener)
    }


    fun addStrikeTimeListener (listener: StrikeTimeInterface){
        StrikeTime.add(listener)
    }

    fun notifyStrike(time: Int){
        for(listener in StrikeTime){
            listener.onStrike(time)
        }
    }

    fun notifyRemoveStrike(){
        for(listener in StrikeTime){
            listener.RemoveStrike()
        }
    }

    fun notifyCompletedQuestions(){
        for(listener in StrikeTime){
            listener.onLevelComplete()
        }
    }
    fun notifyException(message: String) {
        for(listener in ExceptionInterfaceList){
            listener.onNetworkException(message)
        }
    }


}

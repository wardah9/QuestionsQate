package com.questionqate.Utilties

import com.questionqate.Interface.StrikeTimeInterface
import com.questionqate.Interface.Exceptions
import io.reactivex.rxkotlin.toObservable
import java.util.ArrayList

/**
 * Created by CodeCrazy on 6/18/17.
 */

object EventBus {


    internal var ExceptionInterfaceList: MutableList<Exceptions> = ArrayList()
    internal var StrikeTime: MutableList<StrikeTimeInterface> = ArrayList()


    fun removeStrikeTimeListener(listener: StrikeTimeInterface){
        StrikeTime.remove(listener)
    }


    fun addExceptionsListener (listener: Exceptions){
        ExceptionInterfaceList.add(listener)
    }


    fun addStrikeTimeListener (listener: StrikeTimeInterface){
        StrikeTime.add(listener)
    }

    fun notifyStrike(time: Int){

       StrikeTime.last().onStrike(time)

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

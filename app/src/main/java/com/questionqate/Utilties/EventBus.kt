package com.questionqate.Utilties

import com.questionqate.Interface.StrikeTimeInterface
import com.questionqate.Interface.Exceptions
import com.questionqate.Interface.LecturerChoices
import io.reactivex.rxkotlin.toObservable
import java.util.ArrayList

/**
 * Created by CodeCrazy on 6/18/17.
 */

object EventBus {


    internal var ExceptionInterfaceList: MutableList<Exceptions> = ArrayList()
    internal var StrikeTime: MutableList<StrikeTimeInterface> = ArrayList()
    internal var LecturerInterfaceList: MutableList<LecturerChoices> = ArrayList()


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

    fun addLecturerListner (listener: LecturerChoices){
        LecturerInterfaceList.add(listener)
    }

    fun removeLecturerListner (listener: LecturerChoices){
        LecturerInterfaceList.remove(listener)
    }

    fun notifyLecturerlevelChange(level: Int){
        LecturerInterfaceList.last().onLecturerLevelChange(level)
    }

    fun notifyLecturerSubjectChange(subject: String){
        LecturerInterfaceList.last().onLecturerSubjectChange(subject)
    }
}

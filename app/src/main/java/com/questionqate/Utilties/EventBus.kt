package com.questionqate.Utilties

import com.questionqate.Networking.Exceptions
import java.util.ArrayList

/**
 * Created by CodeCrazy on 6/18/17.
 */

object EventBus {


    internal var ExceptionInterfaceList: MutableList<Exceptions> = ArrayList()

    fun addExceptionsListener (listener: Exceptions){
        ExceptionInterfaceList.add(listener)
    }

    fun notifyException(message: String) {
        for(listener in ExceptionInterfaceList){
            listener.onNetworkException(message)
        }
    }


}

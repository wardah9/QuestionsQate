package css.fingerprint.Networking


import android.util.Log
import com.questionqate.Networking.REST
import com.questionqate.Utilties.EventBus
import io.reactivex.Observable
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.Response
import java.io.IOException


/**
 * Created by CodeCrazy on 6/23/17.
 */


object OkhttpObservable {

    fun getObservable(url: String, params: HashMap<String, String>?): Observable<Response> {
        return Observable.create<Response> { o -> REST.getInstance().get(url, params, object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                if (e != null) {
             //       EventBus.publishException(e.message!!)
                    call!!.cancel()
                }
            }

            override fun onResponse(call: Call?, response: Response?) {
                if (response != null) {
                    o.onNext(response)
                }

                o.onComplete()
            }
        })
        }
    }

    fun post(url: String, params: FormBody.Builder): Observable<Response> {
        return Observable.create<Response> { o -> REST.getInstance().post(url, params, object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                if (e != null) {
                    Log.wtf("correctanswer",e.toString())
                    EventBus.notifyException(e.message!!)
                    call!!.cancel()


                }
            }

            override fun onResponse(call: Call?, response: Response?) {
                if (response != null) {
                   // println(response.body()!!.string())
                    o.onNext(response)
                    o.onComplete()

                }
            }
        })
        }
    }




}

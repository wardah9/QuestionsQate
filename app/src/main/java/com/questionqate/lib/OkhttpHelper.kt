package css.fingerprint.Networking


import io.reactivex.Observable
import okhttp3.*
import java.io.IOException
import java.util.*


/**
 * Created by CodeCrazy on 6/23/17.
 */


object OkhttpHelper {

    private val client: OkHttpClient?
    private var builder: FormBody.Builder? = null
    private var formBody: RequestBody? = null
    private var call: Call? = null
   // private var  tokenHelper: Token

    init {
        client = OkHttpClient().newBuilder().build()
     //   tokenHelper = Token

    }

    fun getOhttpClient() : OkHttpClient? {
        return client
    }

    fun get(url: String, params: HashMap<String, String>?, callback: Callback) {
        builder = FormBody.Builder()

        if (params != null) {
            for ((key, value) in params) {
                builder!!.add(key, value)
            }
        }

        formBody = builder!!.build()

        val request = Request.Builder()
                .url(url)
                .get()
                .build()

        call = client!!.newCall(request)
        call!!.enqueue(callback)
    }

    fun post(url: String, params: HashMap<String, String>?, callback: Callback) {
        builder = FormBody.Builder()

        if (params != null) {
            for ((key, value) in params) {
                builder!!.add(key, value)
            }
        }

        formBody = builder!!.build()

        val request = Request.Builder()
                .url(url)
                .post(formBody!!)
                .build()

        call = client!!.newCall(request)
        call!!.enqueue(callback)
    }




    fun getObservable(url: String, params: HashMap<String, String>?): Observable<Response> {
        return Observable.create<Response> { o -> get(url, params, object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                if (e != null) {
                  //  EventBus.publishException(e.message!!)
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

    fun postObservable(url: String, params: HashMap<String, String>?): Observable<Response> {
        return Observable.create<Response> { o -> post(url, params, object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                if (e != null) {
          //          EventBus.publishException(e.message!!)
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




}

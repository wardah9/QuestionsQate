package com.questionqate.Networking;



import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by mouhsinebakhich on 12/19/16.
 */
public class REST {
  //  private final Exceptions exception = Exceptions.getInstance();

    private OkHttpClient client;
    private FormBody.Builder builder;
    private HttpUrl.Builder urlBuilder;
    private RequestBody formBody;
    private Call call;


//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static REST instance;
    private REST() {

            client = new OkHttpClient.Builder()
                    .build();


    }
    public static REST getInstance() {
        if (instance == null) instance = new REST();
        return instance;
    }


    public void get(String url, HashMap<String ,String> params, Callback callback) {
        urlBuilder = HttpUrl.parse(url).newBuilder();
        builder = new FormBody.Builder();

        if(params!=null){
            for ( Map.Entry<String, String> entry : params.entrySet() ) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        url = urlBuilder.build().toString();
        formBody = builder.build();
        System.out.print(url);
        Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

        call = client.newCall(request);
        call.enqueue(callback);
    }


    public void post(String url, FormBody.Builder builder, Callback callback) {

        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .url(url)
                .post(builder.build())
                .build();

        call = client.newCall(request);
        call.enqueue(callback);
    }


}

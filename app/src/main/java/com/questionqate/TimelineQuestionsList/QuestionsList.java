package com.questionqate.TimelineQuestionsList;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.questionqate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class QuestionsList extends AppCompatActivity {

    private AndroidNetworking ndroidNetworking;

    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mDataList = new ArrayList<>();
    private Orientation mOrientation = Orientation.VERTICAL;

    public enum Orientation {
        VERTICAL
    }

    public enum OrderStatus {

        COMPLETED,
        ACTIVE,
        INACTIVE;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_list);
//        setTitle(getResources().getString(R.string.vertical_timeline));


//        ndroidNetworking.post("https://us-central1-questionsqate-9a3d7.cloudfunctions.net/getSubjects")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
////                        Toast.makeText(Main3Activity.this, "Result is"+response, Toast.LENGTH_SHORT).show();
//                        Log.d("result",response.toString());
//                        try {
//                            JSONArray Levels = response.getJSONArray("levels");
//                            for (int i =0; i>Levels.length(); i++){
//                                Toast.makeText(QuestionsList.this, "i is "+ Levels.get(i).toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {e.printStackTrace();}
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                        Toast.makeText(QuestionsList.this, "NO data Found :(", Toast.LENGTH_SHORT).show();
//                    }
//                });

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

        ndroidNetworking.post("https://us-central1-questionsqate-9a3d7.cloudfunctions.net/getSubjects")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {

                    @Override
                    public void onResponse(JSONArray response) {
                      //  Log.d("questionArray",response.toString());

                        Observable.fromArray(response)
                                .map(e->e.getJSONObject(1).getJSONArray("levels")
                                        .getJSONObject(1)) //TODO add dynamic 1,2,3 for level
                                .map(e->e.getJSONArray("Questions"))
                                .doOnNext(e-> Log.d("questionArray",e.toString()))
                                .doOnNext(e->mRecyclerView.setAdapter(new QuestionAdapter(e)))
                        .subscribe();




                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });



        initView();
    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void initView() {
        setDataListItems();
     //   mTimeLineAdapter = new TimeLineAdapter(mDataList, mOrientation);
       // mRecyclerView.setAdapter(mTimeLineAdapter);
    }


    private void setDataListItems() {
        mDataList.add(new TimeLineModel("Item successfully delivered", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Courier is out to delivery your order", OrderStatus.ACTIVE));
        mDataList.add(new TimeLineModel("Item has reached courier facility at New Delhi", OrderStatus.INACTIVE));
        mDataList.add(new TimeLineModel("Item has been given to the courier", OrderStatus.INACTIVE));
        mDataList.add(new TimeLineModel("Item is packed and will dispatch soon", OrderStatus.INACTIVE));
        mDataList.add(new TimeLineModel("Order is being readied for dispatch", OrderStatus.INACTIVE));
        mDataList.add(new TimeLineModel("Order processing initiated", OrderStatus.INACTIVE));
        mDataList.add(new TimeLineModel("Order confirmed by seller", OrderStatus.INACTIVE));
        mDataList.add(new TimeLineModel("Order placed successfully", OrderStatus.INACTIVE));
    }

}


class TimeLineModel implements Parcelable {

    private String mMessage;
    private QuestionsList.OrderStatus mStatus;

    public TimeLineModel() {
    }

    public TimeLineModel(String mMessage, QuestionsList.OrderStatus mStatus) {
        this.mMessage = mMessage;

        this.mStatus = mStatus;
    }

    public String getMessage() {
        return mMessage;
    }

    public void semMessage(String message) {
        this.mMessage = message;
    }

    public QuestionsList.OrderStatus getStatus() {
        return mStatus;
    }

    public void setStatus(QuestionsList.OrderStatus mStatus) {
        this.mStatus = mStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mMessage);
//        dest.writeString(this.mDate);
        dest.writeInt(this.mStatus == null ? -1 : this.mStatus.ordinal());
    }

    protected TimeLineModel(Parcel in) {
        this.mMessage = in.readString();
        int tmpMStatus = in.readInt();
        this.mStatus = tmpMStatus == -1 ? null : QuestionsList.OrderStatus.values()[tmpMStatus];
    }

    public static final Parcelable.Creator<TimeLineModel> CREATOR = new Parcelable.Creator<TimeLineModel>() {
        @Override
        public TimeLineModel createFromParcel(Parcel source) {
            return new TimeLineModel(source);
        }

        @Override
        public TimeLineModel[] newArray(int size) {
            return new TimeLineModel[size];
        }
    };
}

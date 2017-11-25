package com.maryam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.questionqate.R;

import java.util.ArrayList;

public class ViewTrainer extends AppCompatActivity {
    private ListView FierbaseTrainerList;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    ArrayList<getTrainer> Trainerlist;
    private FirebaseStorage storage;
    private StorageReference mStorageRef;
}
//    TextView test;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_trainer);
//
//
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("Trainer");
//        test= (TextView) findViewById(R.id.textView16);
//        FierbaseTrainerList = (ListView) findViewById(R.id.trainer_list);
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Trainerlist = new ArrayList<getTrainer>();
//                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
//
//                    Log.v("firbase", "key is : " + postSnapShot.getKey()
//                            + " value  is " + postSnapShot.getValue());
//
//                    getTrainer getTrainer = postSnapShot.getValue(getTrainer.class);
//                    //just to know the retriveing value
//                    assert getTrainer != null;
//                    getTrainer.getTrainerEmail();
//                    getTrainer.getTrainerName();
//                    getTrainer.getTrainerPhone();
//                    getTrainer.getTrainerID();
//                    getTrainer.Trainercivilno();
//                    getTrainer.Tupe();
//                    Trainerlist.add(getTrainer);
//                }
//                FierbaseTrainerList.setAdapter(new CustomAdapter(Trainerlist));
//
//            }
//
//
//
//
//        @Override
//        public void onCancelled(DatabaseError error) {
//            // Failed to read value
//            Log.w("firebase", "Failed to read value.", error.toException());
//        }
//    });
//}
//    class CustomAdapter extends BaseAdapter {
//
//        ArrayList<getTrainer> Trainerlist;
//
//        public CustomAdapter(ArrayList<getTrainer> Trainerlist) {
//            this.Trainerlist = Trainerlist;
//        }
//
//        @Override
//        public int getCount() {
//            return Trainerlist.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//            View view2 = inflater.inflate(R.layout.trainer_details, null);
//
//            TextView name = (TextView) view2.findViewById(R.id.Tname);
//
//
//            getTrainer trainerD= Trainerlist.get(i);
//
//            name.setText(trainerD.getTrainerName());
//
//
//            return view2;
//        }
//    }
//}


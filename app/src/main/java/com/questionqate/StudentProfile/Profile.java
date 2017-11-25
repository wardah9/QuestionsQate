package com.questionqate.StudentProfile;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.questionqate.Pojo.Student;
import com.questionqate.R;


import java.io.FileNotFoundException;
import java.io.InputStream;

public class Profile extends AppCompatActivity {

    ImageView profile_image;
    TextView profile_name;
    TextView profile_mail;
    TextView profile_phone;
    EditText profile_name_edt;
    EditText profile_mail_edt;
    EditText profile_phone_edt;
    Button edit_Profile;

    private int SELECT_FROM_GALLARY = 2;
    private int SELECT_FROM_CAMERA = 1;

    private DatabaseReference myRef;
    private FirebaseDatabase database;

    String baseMob = "";
    private String TAG = "firebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile_image = findViewById(R.id.profile_image);
        edit_Profile = findViewById(R.id.edit_Profile);

        profile_name = findViewById(R.id.profile_name);
        profile_mail = findViewById(R.id.profile_mail);
        profile_phone = findViewById(R.id.profile_phone);


        profile_name_edt = findViewById(R.id.profile_name_edt);
        profile_mail_edt = findViewById(R.id.profile_mail_edt);
        profile_phone_edt = findViewById(R.id.profile_phone_edt);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("My_students");

        //good example to avoid late response..
        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {

                    Log.v("firbase", "key is : " + postSnapShot.getKey()
                            + " value  is " + postSnapShot.getValue());

                    Student eventListener = dataSnapshot.getValue(Student.class);
                    //just to know the retrieving value
                    assert eventListener != null;
                    profile_name.setText(eventListener.getUserName());
                    profile_mail.setText(eventListener.getUserEmail());
                    profile_phone.setText(eventListener.getUserMobile());

                    baseMob = eventListener.getUserMobile();

                    profile_name_edt.setText(eventListener.getUserName());
                    profile_mail_edt.setText(eventListener.getUserEmail());
                    profile_phone_edt.setText(eventListener.getUserMobile());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("firebase", "Failed to read data.", error.toException());
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflator != null) {
                View v = inflator.inflate(R.layout.ic_edit, null);

                ImageView edit_pro = v.findViewById(R.id.edit_pro);
                edit_pro.setOnClickListener(view -> {

                    profile_phone.setVisibility(View.GONE);
                    profile_mail.setVisibility(View.GONE);
                    profile_name.setVisibility(View.GONE);

                    profile_name_edt.setVisibility(View.VISIBLE);
                    profile_mail_edt.setVisibility(View.VISIBLE);
                    profile_phone_edt.setVisibility(View.VISIBLE);

                });
                actionBar.setCustomView(v);
            }
        }


        profile_image.setOnClickListener(view ->
        {
            final Dialog dialog = new Dialog(Profile.this);
            dialog.setContentView(R.layout.custom_dialog_ex2);
            Button camera_button = (Button) dialog.findViewById(R.id.cc);
            Button gallery_button = (Button) dialog.findViewById(R.id.gg);

            camera_button.setOnClickListener(v -> {
                if (ContextCompat.checkSelfPermission(Profile.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Profile.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {
                    Intent cameraIntent = new Intent();
                    cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, SELECT_FROM_CAMERA);
                }
                dialog.dismiss();
            });
            gallery_button.setOnClickListener(v -> {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_FROM_GALLARY);
                dialog.dismiss();
            });
            dialog.show();

        });

        edit_Profile.setOnClickListener(view -> {

            if (profile_phone_edt.getText().toString().equals(baseMob)){

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                user.updateEmail(profile_mail_edt.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User email address updated.");
                                myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userEmail").setValue(profile_mail_edt.getText().toString());
                                myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userName").setValue(profile_name.getText().toString());
                                Toast.makeText(this, "User email address updated.", Toast.LENGTH_SHORT).show();
                            }else
                                Log.d(TAG, "User email address not updated.");
                        });
            }else {
                Intent in = new Intent(this,UpdatedMobileAthuntication.class);
                in.putExtra("MobileNo",baseMob);
                startActivity(in);
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SELECT_FROM_CAMERA && resultCode == RESULT_OK) {

            Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data");
            profile_image.setImageBitmap(cameraBitmap);
        } else if (requestCode == SELECT_FROM_GALLARY && resultCode == RESULT_OK) {

            try {
                assert (data == null ? null : data.getData()) != null;
                InputStream input = getContentResolver().openInputStream(data.getData());
                final Bitmap bitmap = BitmapFactory.decodeStream(input, null, new BitmapFactory.Options());

                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                profile_image.setImageDrawable(drawable);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "Error occurred ! ,  " + resultCode, Toast.LENGTH_SHORT).show();
        }
    }

}

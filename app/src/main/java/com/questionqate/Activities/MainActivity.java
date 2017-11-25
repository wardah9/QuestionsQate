package com.questionqate.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.questionqate.R;
import com.questionqate.Pojo.Global_Strings;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private EditText email;
    private EditText pass;

    private Button loginBtn;
    private TextView forgetPass;
    private TextView signup;

    private CheckBox rememberMe;
    private SharedPreferences sharedPreferences;
    private boolean check = false;
    private String userID;

    Global_Strings global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        global = new Global_Strings();
//
//        if (sharedPreferences.getString("username", "name") != null &&
//                sharedPreferences.getString("password", "password") != null) {
//
//            userID = sharedPreferences.getString("user_id", "id");
//            startActivity(new Intent(MainActivity.this, StudentSlideMenu.class).putExtra("user_id", userID));
//            finish();
//        }



        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.UserName);
        pass = (EditText) findViewById(R.id.Password);
        rememberMe = (CheckBox) findViewById(R.id.rememberMe);
//        loginBtn = (Button) findViewById(R.id.login_btn);
        forgetPass = (TextView) findViewById(R.id.forget_pass);
        signup = (TextView) findViewById(R.id.signup);

//        loginBtn.setOnClickListener(this);
        forgetPass.setOnClickListener(this);
        signup.setOnClickListener(this);


        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    check = true;
                else
                    check = false;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.login_btn:
//                startActivity(new Intent(this,StudentSlideMenu.class));
//                break;
            case R.id.forget_pass:
                startActivity(new Intent(MainActivity.this, ForgetPasswordActivity.class));
                break;
            case R.id.signup:
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
                break;
        }
    }

    public void OnLoginClicked(View view) {

        mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("firebaseLogin", "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w("firebaseLogin", "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "Login failed",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Login success ", Toast.LENGTH_SHORT).show();
                            getUID();

                        }
                    }
                });
    }


    private void getUID() {

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

            if (check == true) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", email.getText().toString());
                editor.putString("password", pass.getText().toString());
                editor.putString("user_id", user.getUid());
                editor.commit();
               // Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
                global.setStudent_UID_firebase(user.getUid());
                startActivity(new Intent(MainActivity.this, StudentSlideMenu.class));
            } else {
               // Toast.makeText(this, "not saved", Toast.LENGTH_SHORT).show();
                global.setStudent_UID_firebase(user.getUid());
                startActivity(new Intent(MainActivity.this, StudentSlideMenu.class));
            }
        } else {
            // User is signed out
            Log.d("firebase", "onAuthStateChanged:signed_out");
        }
    }
}

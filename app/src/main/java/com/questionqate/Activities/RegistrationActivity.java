package com.questionqate.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.questionqate.R;
import com.questionqate.Pojo.Student;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth uAuth;
    private EditText email_reg;
    private EditText name_reg;
    private EditText pass_reg;
    private EditText id_reg;
    private EditText retype_reg;
//    private EditText mobile_reg;

    Student student;

//    private DatabaseReference myRef;
//    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("Test1");

        uAuth = FirebaseAuth.getInstance();

        name_reg = (EditText) findViewById(R.id.name_reg);
        id_reg = (EditText) findViewById(R.id.id_reg);
        retype_reg = (EditText) findViewById(R.id.retype_reg);
        email_reg = (EditText) findViewById(R.id.email_reg);
        pass_reg = (EditText) findViewById(R.id.pass_reg);
//        mobile_reg = (EditText) findViewById(R.id.mobile_reg);


        Button reg_btn = (Button) findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uAuth.createUserWithEmailAndPassword(email_reg.getText().toString(), pass_reg.getText().toString())
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("firebaseLogin", "createUserWithEmail:onComplete:" + task.isSuccessful());

                                if (task.isSuccessful()) {
                                    Toast.makeText(RegistrationActivity.this, "register successful", Toast.LENGTH_SHORT).show();
                                    AddUserInfo();
                                } else
                                    Toast.makeText(RegistrationActivity.this, "register failed ", Toast.LENGTH_SHORT).show();
                            }

                        });
            }

            private void AddUserInfo() {

                if (Objects.equals(pass_reg.getText().toString(), retype_reg.getText().toString())) {
                    Toast.makeText(RegistrationActivity.this, "test", Toast.LENGTH_SHORT).show();
                    String key = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    Intent so = new Intent(RegistrationActivity.this, PhoneAthunticationActivity.class);
                    so.putExtra("key", key);
                    so.putExtra("name",name_reg.getText().toString());
                    so.putExtra("id",id_reg.getText().toString());
                    so.putExtra("email",email_reg.getText().toString());
                    so.putExtra("pass",pass_reg.getText().toString());
                    startActivity(so);

                } else {
                    Toast.makeText(RegistrationActivity.this, "Password not match", Toast.LENGTH_SHORT).show();
                    pass_reg.setText("");
                    retype_reg.setText("");
                }
            }

        });

    }
}

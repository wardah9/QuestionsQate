package com.questionqate.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.questionqate.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mAuth = FirebaseAuth.getInstance();
        email_forget = (EditText) findViewById(R.id.email_forget);
    }

    public void OnResetPasswordClicked(View view) {
        mAuth.sendPasswordResetEmail(email_forget.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgetPasswordActivity.this, "Password send to ur email", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ForgetPasswordActivity.this, "Password not send you have to check email again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

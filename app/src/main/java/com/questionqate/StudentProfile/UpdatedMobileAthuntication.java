package com.questionqate.StudentProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.questionqate.Activities.StudentSlideMenu;
import com.questionqate.Pojo.Student;
import com.questionqate.R;


import java.util.concurrent.TimeUnit;

public class UpdatedMobileAthuntication extends AppCompatActivity implements View.OnClickListener {

    private EditText updatePhoneNumber;
    private EditText verificationCode;
    private Button startBtn;
    private Button verifyBtn;
    private Button resendBtn;


    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    Student student;
    String mVerificationId;
    private static final String TAG = "PhoneAuthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updated_mobile_athuntication);

        Intent intent = getIntent();
        String mobile = intent.getStringExtra("MobileNo");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("My_students");
        mAuth = FirebaseAuth.getInstance();

        updatePhoneNumber = findViewById(R.id.update_phone_number);
        verificationCode = findViewById(R.id.verification_code);
        startBtn = findViewById(R.id.start_btn);
        verifyBtn = findViewById(R.id.verify_btn);
        resendBtn = findViewById(R.id.resend_btn);

        updatePhoneNumber.setText(mobile);

        startBtn.setOnClickListener(this);
        verifyBtn.setOnClickListener(this);
        resendBtn.setOnClickListener(this);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
//                signInWithPhoneAuthCredential(credential);
                UpdateMobileNumberAthu(credential);
            }


            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    updatePhoneNumber.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };

    }

    private void UpdateMobileNumberAthu(PhoneAuthCredential credential) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        user.updatePhoneNumber(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "User mobile updated.", Toast.LENGTH_SHORT).show();
                myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userMobile").setValue(updatePhoneNumber.getText().toString());
                startActivity(new Intent(UpdatedMobileAthuntication.this, StudentSlideMenu.class));
                finish();
            } else
                Log.d(TAG, "User mobile not updated.");
            Toast.makeText(this, "User mobile not updated.", Toast.LENGTH_SHORT).show();

        });

    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//        signInWithPhoneAuthCredential(credential);
        UpdateMobileNumberAthu(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }


    private boolean validatePhoneNumber() {
        String phoneNumber = updatePhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            updatePhoneNumber.setError("Invalid phone number.");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.start_btn:
                if (!validatePhoneNumber()) {
                    return;
                }
                startPhoneNumberVerification(updatePhoneNumber.getText().toString());
                break;
            case R.id.verify_btn:
                String code = updatePhoneNumber.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    updatePhoneNumber.setError("Cannot be empty.");
                    return;
                }
                verifyPhoneNumberWithCode(mVerificationId, code);
                break;
            case R.id.resend_btn:
                resendVerificationCode(updatePhoneNumber.getText().toString(), mResendToken);
                break;
        }
    }
}


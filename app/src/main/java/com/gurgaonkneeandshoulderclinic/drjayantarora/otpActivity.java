package com.gurgaonkneeandshoulderclinic.drjayantarora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otpActivity extends AppCompatActivity {

    private Button verifyButton;
    private EditText otpText;
    public String verificationCodeBySystem;

    // public variables

    public String userNumber;
    public String userName;


    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        mAuth = FirebaseAuth.getInstance();

        String phone = getIntent().getStringExtra("number");
        sendVerificationCode  (phone);

        String code = getIntent().getParcelableExtra("code");
        String number = getIntent().getParcelableExtra("number");
        String name = getIntent().getParcelableExtra("name");

        userNumber = number;
        userName = name;



        // Actionbar
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0096FF"));

        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("OTP Confirmation");

        verifyButton = (Button) findViewById(R.id.verifyButton);
        otpText = (EditText) findViewById(R.id.otpText);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode(otpText.getText().toString());

            }
        });


    }

    private void sendVerificationCode (String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNo,
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);

                        verificationCodeBySystem = s;


                        // Success

                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        String code = phoneAuthCredential.getSmsCode();
                        verifyCode(code);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    private void verifyCode (String codeByUser) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            // Update UI
                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);

                            intent.putExtra("name", getIntent().getStringExtra("name"));
                            intent.putExtra("number", getIntent().getStringExtra("number"));

                            startActivity(intent);
                        } else {
                            // Sign in failed, display a message and update the UI

                            Toast.makeText(getApplicationContext(), "The OTP entered is incorrect, please try again.", Toast.LENGTH_LONG).show();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}
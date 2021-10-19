package com.gurgaonkneeandshoulderclinic.drjayantarora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private Button privacyPolicyButton;
    private Button loginButton;

    private EditText numberText;
    private EditText nameText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // mAuth


        // Actionbar
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0096FF"));

        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Login");

        // EditTexts
        numberText = (EditText) findViewById(R.id.numberText);
        nameText = (EditText) findViewById(R.id.nameText);

        // Login Button
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (numberText.getText().toString().matches("") || nameText.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "One or more fields have been left empty. Please fill in all fields.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Intent intent = new Intent(getApplicationContext(), otpActivity.class);
                    intent.putExtra("name", nameText.getText().toString());
                    intent.putExtra("number", numberText.getText().toString());
                    startActivity(intent);

                }
            }
        });

        // Privacy Policy Button
        privacyPolicyButton = (Button) findViewById(R.id.privacyPolicyButton);
        privacyPolicyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPrivacyPolicy();
            }
        });

        }

        public void openPrivacyPolicy() {
            Intent openWebsite = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gurgaonkneeandshoulderclinic.com/privacy-policy"));
            startActivity(openWebsite);
    }


}
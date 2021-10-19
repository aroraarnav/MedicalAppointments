package com.gurgaonkneeandshoulderclinic.drjayantarora;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    private Button proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Actionbar
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0096FF"));

        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Welcome");

        //Button
        proceedButton = (Button) findViewById(R.id.proceedButton);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginPage();
            }
        });


    }

    public void openLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
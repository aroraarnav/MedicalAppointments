package com.gurgaonkneeandshoulderclinic.drjayantarora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        String noSpaceName = getIntent().getStringExtra("name").replaceAll("\\s+", "");
        String noSpaceDate = getIntent().getStringExtra("date").replaceAll("\\s+", "");
        String noSpaceTime = getIntent().getStringExtra("time").replaceAll("\\s+", "");


        // Actionbar
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0096FF"));

        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Success");

        // Messages
        OkHttpClient userClient = new OkHttpClient();
        String userUrl = "https://platform.clickatell.com/messages/http/send?apiKey=hzmD9x7aQPa3FE2l8CJqlg==&to=91" + getIntent().getStringExtra("number") + "&content=Thank+you+for+booking+your+appointment+with+Dr+Jayant+Arora.+The+details+are+as+follows:+Date:+" + noSpaceDate + "+Time:+" + noSpaceTime + "Please be there 5 minutes prior to the scheduled time.+Clinic's location:+https://bit.ly/3mP7aq9 For any queries, please contact the clinic reception at: 919999847468";

        Request userRequest = new Request.Builder()
                .url(userUrl)
                .build();
        userClient.newCall(userRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });

        OkHttpClient adminClient = new OkHttpClient();
        String adminUrl = "https://platform.clickatell.com/messages/http/send?apiKey=hzmD9x7aQPa3FE2l8CJqlg==&to=919999847468&content=Date:+" + noSpaceDate + "+Time:+" + noSpaceTime + "Name:+" + noSpaceName + "Phone:+" + getIntent().getStringExtra("number");

        Request adminRequest = new Request.Builder()
                .url(adminUrl)
                .build();
        adminClient.newCall(adminRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });





    }

    // Messages
}
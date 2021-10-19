package com.gurgaonkneeandshoulderclinic.drjayantarora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, PaymentResultListener {

    private static final String TAG = "";
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    Button payButton;
    Spinner timeSpinner;

    private String userTime;
    private String pricing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);


        FirebaseDatabase.getInstance().getReference().child("price").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pricing = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Checkout.preload(getApplicationContext());

        // INITs
        payButton = (Button) findViewById(R.id.payButton);
        timeSpinner = (Spinner) findViewById(R.id.timeSpinner);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startPayment();

            }
        });

        // Actionbar
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0096FF"));

        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Select Your Time");

        // Database INIT
        rootNode = FirebaseDatabase.getInstance();

        // Day and date
        String day = getIntent().getStringExtra("day");
        String date = getIntent().getStringExtra("date");

        // Data retrieval
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        timeSpinner.setAdapter(adapter);
        timeSpinner.setOnItemSelectedListener(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Ortho Consult").child("Time Slots").child(day);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String value = dataSnapshot.getValue().toString();
                    if (!value.equals("booked for " + getIntent().getStringExtra("day") + " " + getIntent().getStringExtra("date"))) {
                        list.add(dataSnapshot.getKey().toString());
                    }

                    adapter.notifyDataSetChanged();

                }


            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        userTime = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // RAZORPAY

    public void startPayment() {
        // checkout.setKeyID("<YOUR_KEY_ID>");
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        // checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Dr Jayant Arora");
            options.put("description", "Consultation Fee");
            options.put("image", "https://image3.mouthshut.com/images/imagesp/926076953s.png");
            // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", pricing);//pass amount in currency subunits
            // options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact", getIntent().getStringExtra("number"));

            // JSONObject retryObj = new JSONObject();
            // retryObj.put("enabled", true);
            // retryObj.put("max_count", 4);
            // options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }

    }
    @Override
    public void onPaymentSuccess(String s) {


        FirebaseDatabase.getInstance().getReference().child("Ortho Consult").child("Time Slots").child(getIntent().getStringExtra("day")).child(userTime).setValue("booked for " + getIntent().getStringExtra("day") + " " + getIntent().getStringExtra("date"));
        Intent intent = new Intent(getApplicationContext(), SuccessActivity.class);
        intent.putExtra("name", getIntent().getStringExtra("name"));
        intent.putExtra("number", getIntent().getStringExtra("number"));
        intent.putExtra("date", getIntent().getStringExtra("date"));
        intent.putExtra("day", getIntent().getStringExtra("day"));
        intent.putExtra("time", userTime);
        startActivity (
                intent
        );

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Something went wrong, please try again.", Toast.LENGTH_LONG).show();
        Log.d("Error", s);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

        startActivity(intent);
    }
}
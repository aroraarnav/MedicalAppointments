package com.gurgaonkneeandshoulderclinic.drjayantarora;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private TextView welcomeText;

    // Buttons
    private Button bookButton;
    private Button websiteButton;
    private Button contactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Buttons
        bookButton = (Button) findViewById(R.id.bookButton);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker ();
            }
        });

        websiteButton = (Button) findViewById(R.id.websiteButton);
        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openWebsite ();

            }
        });

        contactButton = (Button) findViewById(R.id.contactButton);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:9999847468"));
                startActivity(callIntent);
            }
        });



        // Welcome text
        welcomeText = (TextView) findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome, " + getIntent().getStringExtra("name"));

        // Actionbar
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0096FF"));

        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Dashboard");
    }

    public void openWebsite () {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://www.gurgaonkneeandshoulderclinic.com"));
        startActivity(intent);
    }

    public void openDatePicker () {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)



        );
        datePickerDialog.show();

        String date = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        try{
            Date finalDate = df.parse(date);

            // MIN
            Calendar min = Calendar.getInstance();
            min.setTime(finalDate);
            Date minDate = min.getTime();

            // MAX
            Calendar max = Calendar.getInstance();
            max.setTime(finalDate);
            max.add(Calendar.DATE, 6);
            Date maxDate = max.getTime();

            long minLong = minDate.getTime();
            long maxLong = maxDate.getTime();

            datePickerDialog.getDatePicker().setMinDate(minLong);
            datePickerDialog.getDatePicker().setMaxDate(maxLong);

        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = df.parse(selectedDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            Date newDate = calendar.getTime();

            Log.d("d", new SimpleDateFormat("EE", Locale.getDefault()).format(newDate.getTime()));

            if (new SimpleDateFormat("EE", Locale.getDefault()).format(newDate.getTime()).equals("Sun")) {
                Toast.makeText(this, "Sorry, our services are not available on Sundays.", Toast.LENGTH_LONG).show();
            } else {

                Intent intent = new Intent(getApplicationContext(), BookActivity.class);
                intent.putExtra("name", getIntent().getStringExtra("name"));
                intent.putExtra("number", getIntent().getStringExtra("number"));
                intent.putExtra("date", selectedDate);
                intent.putExtra("day", new SimpleDateFormat("EEEE", Locale.getDefault()).format(newDate.getTime()));
                startActivity(intent);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
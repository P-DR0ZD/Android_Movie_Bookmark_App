package com.example.assignment4;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;

public class WatchPartyActivity extends AppCompatActivity implements View.OnClickListener, NetworkingService.NetworkingListener {

    NetworkingService networkingManager;
    JsonService jsonManager;

    EditText location;
    TextView time;
    TextView date;

    ImageView poster;

    TextView header;

    Button dateBtn;
    Button timeBtn;
    Button share;

    ArrayList<Movie> movies = new ArrayList<Movie>();
    Movie data;
    int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watch_party_activity);

        String id = getIntent().getStringExtra("movie");

        networkingManager = ((MyApp)getApplication()).getNetworkingService();
        jsonManager = ((MyApp)getApplication()).getJsonService();

        this.movies = ((MyApp)getApplication()).movies;

        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).imdbID.equals(id)) {
                data = movies.get(i);
            }
        }

        location = findViewById(R.id.watchPrtyLocation);
        time = findViewById(R.id.Time);
        date = findViewById(R.id.Date);

        dateBtn = findViewById(R.id.dateBtn);
        dateBtn.setOnClickListener(this);
        timeBtn = findViewById(R.id.timeBtn);
        timeBtn.setOnClickListener(this);

        poster = findViewById(R.id.posterPrty);

        header = findViewById(R.id.watchPrtyHeader);

        share = findViewById(R.id.shareBtn);
        share.setOnClickListener(this);

        setPage();
    }

    private void setPage() {

        header.setText("Create a watch party for the film " + data.getTitle());

        networkingManager.getImageData(data.getPoster());
        networkingManager.listener = this;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == share.getId()) {
            Uri message = Uri.parse("mailto:");
            Intent send = new Intent(Intent.ACTION_SEND, message);
            send.putExtra(Intent.EXTRA_SUBJECT, "Watch Party!");

            String title = "We are having a Watch party and watching " + data.getTitle() + "" +
                    " which is about " + data.getRunTime() + " in length.\n";

            String tmp = location.getText().toString();
            if (tmp.length() > 0)
            {
                title += "We are having the party at " + tmp + "\n";
            }

            String Date = date.getText().toString();
            String Time = time.getText().toString();

            if (!Date.equals("Select Date"))
            {
                title += "The day of the party is " + Date + "\n";
            }
            if (!Time.equals("Select Time"))
            {
                title += "The time of the party is " + Time + "\n";
            }

            send.putExtra(Intent.EXTRA_TEXT, title);

            try {
                startActivity(send);
            } catch (ActivityNotFoundException e) {
                Log.i("Message",title);
            }
        } else if (id == timeBtn.getId()) {
            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR);
            minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    time.setText(hour + ":" + minute);
                }
                }, hour, minute, false);
            timePickerDialog.show();

        } else if (id == dateBtn.getId()) {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                    date.setText(day + "/" + month + "/" + year);
                }
            }, year, month, day);
            datePickerDialog.show();
        }
    }

    @Override
    public void dataListener(String jsonString) {

    }

    @Override
    public void imageListener(Bitmap image) {
        poster.setImageBitmap(image);
        poster.setVisibility(View.VISIBLE);
    }
}

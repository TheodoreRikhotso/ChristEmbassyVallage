package com.example.admin.christembassyvallage;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.admin.christembassyvallage.model.Announcement;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class AnnouncementActivity extends AppCompatActivity {

    private EditText tvTitle,tvDescr,tvPlace;
    private TextView tvStartDate,tvEndDate,tvTime;
    private String title,description,place,time,startDate,endDate;
    private Button btnSubtmit;
    private DatabaseReference db;
    private FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);


        db = FirebaseDatabase.getInstance().getReference("Announcement");

        tvTitle =(EditText)findViewById(R.id.etAnnTitle);
        tvDescr =(EditText)findViewById(R.id.etAnnDesciption);
        tvPlace =(EditText)findViewById(R.id.etAnnPlace);


        tvStartDate =(TextView)findViewById(R.id.tvAnnStartDate);
        tvEndDate =(TextView)findViewById(R.id.tvAnnEndDate);
        tvTime =(TextView)findViewById(R.id.tvAnnTime);

        btnSubtmit =(Button)findViewById(R.id.btnAnnSumbit);

        fb=(FloatingActionButton) findViewById(R.id.viewAll);







        tvTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                showHourPicker();
            }
        });
        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStartDatePicker();
            }
        });


        btnSubtmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title = tvTitle.getText().toString();
                description = tvDescr.getText().toString();
                place = tvPlace.getText().toString();

                Calendar cal = Calendar.getInstance();
                final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");

                final Date currentTimes = Calendar.getInstance().getTime();

                DateFormat date = new SimpleDateFormat("HH:mm a");


                Announcement ann = new Announcement();
                ann.setStartDate(startDate);
                ann.setEndDate(endDate);
                ann.setTime(time);
                ann.setPlace(place);
                ann.setTitle(title);
                ann.setDesrciption(description);
                ann.setPostedDate(dateFormat.format(cal.getTime()));
                ann.setPostedTime(date.format(currentTimes));


                String id = db.push().getKey();
                db.child(id).setValue(ann);


//                Intent intent =new Intent(getApplicationContext(),AllAnnoumentActivity.class);
//                startActivity(intent);






            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),AllAnnoumentActivity.class);
                startActivity(intent);
            }
        });



    }

    public void showHourPicker() {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);


        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);
                    Calendar currentTimes = Calendar.getInstance();
                    currentTimes.set(hourOfDay,minute);

                    DateFormat date = new SimpleDateFormat("HH:mm a");

                    time =date.format(currentTimes);
                    tvTime.setText(time);

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }
    public void showDatePicker() {
        final Calendar myCalender = Calendar.getInstance();
        int year = myCalender.get(Calendar.YEAR);
        int month = myCalender.get(Calendar.MONTH);
        int dday = myCalender.get(Calendar.DAY_OF_WEEK);

        DatePickerDialog.OnDateSetListener ny = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(i, i1 , i2);

                SimpleDateFormat format1 = new SimpleDateFormat("dd MMMM yyyy");

                endDate =format1.format(calendar);
                tvEndDate.setText(endDate);

            }
        };


        DatePickerDialog timePickerDialog = new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,ny,year,month,dday);
        timePickerDialog.setTitle("End Date:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    public void showStartDatePicker() {
        final Calendar myCalender = Calendar.getInstance();
        int year = myCalender.get(Calendar.YEAR);
        int month = myCalender.get(Calendar.MONTH);
        int dday = myCalender.get(Calendar.DAY_OF_WEEK);

        DatePickerDialog.OnDateSetListener ny = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(i, i1 , i2);

                SimpleDateFormat format1 = new SimpleDateFormat("dd MMMM yyyy");

                startDate =format1.format(calendar);
                tvStartDate.setText(startDate);



            }
        };


        DatePickerDialog timePickerDialog = new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,ny,year,month,dday);
        timePickerDialog.setTitle("Start Date:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }
}

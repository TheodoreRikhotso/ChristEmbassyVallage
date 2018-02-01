package com.example.admin.christembassyvallage;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.admin.christembassyvallage.model.EventPojo;

import java.util.Calendar;

public class NewEventActivity extends AppCompatActivity {

    private EventPojo event;
    private EditText   newDate,tvEventTime;
    private TimePicker tpEventTime;
    private static final int DIALOG_ID=0;
    private int hour_x;
    private int minute_x;
    Button btnSelectDate,btnSelectTime;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID=1;

    // variables to save user selected date and time
    public  int year,month,day,hour,minute;
    // declare  the variables to Show/Set the date and time when Time and  Date Picker Dialog first appears
    private int mYear, mMonth, mDay,mHour,mMinute;

    // constructor

    public NewEventActivity()
    {
        // Assign current Date and Time Values to Variables
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        newDate =(EditText) findViewById(R.id.tvEventDate);
        tvEventTime=(EditText)findViewById(R.id.tvEventTime);


        tvEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  // Show the TimePickerDialog
                showDialog(TIME_DIALOG_ID);

            }
        });
        tvEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the DatePickerDialog
                showDialog(DATE_DIALOG_ID);
            }
        });




        Intent intent = getIntent();
        event = (EventPojo) intent.getSerializableExtra("select");
        newDate.setText(event.getStartDate());
        Toast.makeText(this, event.getStartDate(), Toast.LENGTH_SHORT).show();
    }

    // Register  DatePickerDialog listener
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                // the callback received when the user "sets" the Date in the DatePickerDialog
                public void onDateSet(DatePicker view, int yearSelected,
                                      int monthOfYear, int dayOfMonth) {
                    year = yearSelected;
                    month = monthOfYear;
                    day = dayOfMonth;
                    // Set the Selected Date in Select date Button
                    btnSelectDate.setText("Date selected : "+day+"-"+month+"-"+year);
                }
            };

    // Register  TimePickerDialog listener
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                // the callback received when the user "sets" the TimePickerDialog in the dialog
                public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                    hour = hourOfDay;
                    minute = min;
                    // Set the Selected Date in Select date Button
                    btnSelectTime.setText("Time selected :"+hour+"-"+minute);
                }
            };


    // Method automatically gets Called when you call showDialog()  method
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // create a new DatePickerDialog with values you want to show
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
            // create a new TimePickerDialog with values you want to show
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, mHour, mMinute, false);

        }
        return null;
    }




}

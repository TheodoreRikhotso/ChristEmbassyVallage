package com.example.admin.christembassyvallage;

import android.app.Activity;
import android.app.AlertDialog;
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

import com.applandeo.materialcalendarview.CalendarView;
import com.example.admin.christembassyvallage.model.EventPojo;
import com.example.admin.christembassyvallage.model.MyEventDay;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {
     protected  MyEventDay myEventDay;
     private  String title,description,startDate, enddate, location,selectedTime,guest;

    private EventPojo event,eventPojo;
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

    private EditText etAddTitle,noteEditText,tvNewEventTime,tvNewEventDate,tvNewEventGuest;

    //cale
   private CalendarView datePicker;

    //b
   private Button button;

   //conect to firebase
   private DatabaseReference db;


    // constructor

    public AddNoteActivity()
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
        setContentView(R.layout.activity_add_note);

        Intent intent = getIntent();
        event = (EventPojo) intent.getSerializableExtra("select");

        eventPojo = new EventPojo();


        datePicker = (CalendarView) findViewById(R.id.datePicker);
         button = (Button) findViewById(R.id.addNoteButton);
        noteEditText = (EditText) findViewById(R.id.noteEditText);
        tvEventTime = (EditText)findViewById(R.id.tvEventTime) ;
        tvNewEventDate = (EditText)findViewById(R.id.tvNewEventDate) ;
        etAddTitle = (EditText)findViewById(R.id.etAddTitle) ;
        tvNewEventGuest = (EditText)findViewById(R.id.tvNewEventGuest) ;


        db = FirebaseDatabase.getInstance().getReference("Event");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                 myEventDay = new MyEventDay(datePicker.getSelectedDate(),
                        R.drawable.briefcase, noteEditText.getText().toString());
                eventPojo.setStartDate(datePicker.getSelectedDate().toString());
                String id = db.push().getKey();

                Calendar cal = datePicker.getSelectedDate();

                Date currentTime = cal.getTime();

                DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                String date =  dateFormat.format(currentTime).toString();

               title = etAddTitle.getText().toString();
               description =noteEditText.getText().toString();
                startDate = date;
                eventPojo.setTitle(title);

                eventPojo.setDescription(description);
                eventPojo.setId(id);
                eventPojo.setEnddate(enddate);
                eventPojo.setImageResource(R.drawable.briefcase);
                eventPojo.setTime(selectedTime);

                eventPojo.setNote(noteEditText.getText().toString());



                db.child(id).setValue(eventPojo);
                Toast.makeText(AddNoteActivity.this, "Add note", Toast.LENGTH_SHORT).show();
                returnIntent.putExtra(AllAnnoumentActivity.RESULT, myEventDay);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        newDate =(EditText) findViewById(R.id.tvNewEventDate);
        tvEventTime=(EditText)findViewById(R.id.tvNewEventTime);


        tvEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Show the TimePickerDialog
                showDialog(TIME_DIALOG_ID);

            }
        });
        newDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the DatePickerDialog
                showDialog(DATE_DIALOG_ID);
            }
        });






    }

    // Register  DatePickerDialog listener
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                // the callback received when the user "sets" the Date in the DatePickerDialog
                public void onDateSet(DatePicker view, int yearSelected,
                                      int monthOfYear, int dayOfMonth) {

                    Calendar cal = Calendar.getInstance();
                    cal.set(yearSelected,monthOfYear,dayOfMonth);
                    Date currentTime = cal.getTime();
                    eventPojo.setDay(yearSelected +" "+monthOfYear+" "+dayOfMonth);
                    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                    String date =  dateFormat.format(currentTime).toString();
enddate=date;
                    Toast.makeText(AddNoteActivity.this, ""+date, Toast.LENGTH_SHORT).show();
                    // Set the Selected Date in Select date Button
                  //  btnSelectDate.setText("Date selected : "+dateFormat);
                }
            };

    // Register  TimePickerDialog listener
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                // the callback received when the user "sets" the TimePickerDialog in the dialog
                public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                    hour = hourOfDay;
                    minute = min;


                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR, hourOfDay);
                    cal.set(Calendar.MINUTE, minute);
                    Date currentTime = cal.getTime();
                    DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                    //DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());

String time =dateFormat.format(currentTime).toString();
                    selectedTime =time;
                    Toast.makeText(AddNoteActivity.this, ""+time, Toast.LENGTH_SHORT).show();

                    ///btnSelectTime.setText("Time selected :"+dateFormat);
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
                        AlertDialog.THEME_HOLO_LIGHT,mTimeSetListener, mHour, mMinute, false);

        }
        return null;
    }

}
package com.example.admin.christembassyvallage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.admin.christembassyvallage.model.EventPojo;

public class NewEventActivity extends AppCompatActivity {

    private EventPojo event;
    private EditText   newDate,tvEventTime;
    private TimePicker tpEventTime;
    private static final int DIALOG_ID=0;
    private int hour_x;
    private int minute_x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        newDate =(EditText) findViewById(R.id.tvEventDate);
        tvEventTime=(EditText)findViewById(R.id.tvEventTime);


        tvEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ///showDialog(DIALOG_ID);

            }
        });




        Intent intent = getIntent();
        event = (EventPojo) intent.getSerializableExtra("select");
        newDate.setText(event.getStartDate());
        Toast.makeText(this, event.getStartDate(), Toast.LENGTH_SHORT).show();
    }




}

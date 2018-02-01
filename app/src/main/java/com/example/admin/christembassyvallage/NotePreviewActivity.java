package com.example.admin.christembassyvallage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.example.admin.christembassyvallage.model.MyEventDay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotePreviewActivity extends AppCompatActivity {
private Toolbar toolbarAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_preview);
        Intent intent = getIntent();
        TextView note = (TextView) findViewById(R.id.note);
        toolbarAdd =(Toolbar)findViewById(R.id.toolbarAdd);
        if (intent != null) {
            Object event = intent.getParcelableExtra(AllAnnoumentActivity.EVENT);
            if(event instanceof MyEventDay){
                MyEventDay myEventDay = (MyEventDay)event;
                Toast.makeText(getApplicationContext(),""+ myEventDay.getClass(), Toast.LENGTH_SHORT).show();
                toolbarAdd.setTitle(getFormattedDate(myEventDay.getCalendar().getTime()));
                toolbarAdd.setTitleTextColor(Color.WHITE);
                note.setText(myEventDay.getNote());
                return;
            }
            if(event instanceof EventDay){
                EventDay eventDay = (EventDay)event;
                getSupportActionBar().setTitle(getFormattedDate(eventDay.getCalendar().getTime()));
            }
        }
    }
    public static String getFormattedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return simpleDateFormat.format(date);
    }
}

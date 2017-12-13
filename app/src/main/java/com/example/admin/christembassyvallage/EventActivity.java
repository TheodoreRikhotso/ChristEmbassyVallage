package com.example.admin.christembassyvallage;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.admin.christembassyvallage.model.EventPojo;

import java.util.Date;


public class EventActivity  extends Fragment {

    private static  final String TAG="CalendarActoivity";

    private int count =1;
    private   View view;

    public EventActivity() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_event, container, false);


//
//    }
        EventPojo event=new EventPojo();
CalendarView calendarView =view.findViewById(R.id.eventCalendar);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {

               EventPojo event=new EventPojo();

               GregorianCalendar c = new GregorianCalendar( year, month, dayOfMonth+1 );


                    if(count ==2) {
                        Date newDate = new Date(String.valueOf(c.getTime()));
                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                        String changeDate = sdf.format(newDate);
                        Toast.makeText(getActivity(), "" + changeDate, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), NewEventActivity.class);
                        event.setStartDate(changeDate);

                        intent.putExtra("select", event);
                        startActivity(intent);

                    }else {
                        count++;
                    }






            }

        });
//        count=0;

return view;
    }
}

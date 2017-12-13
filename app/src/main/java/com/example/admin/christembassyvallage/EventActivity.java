package com.example.admin.christembassyvallage;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class EventActivity  extends Fragment {

    private static  final String TAG="CalendarActoivity";

    private int count =1;
    private   View view;
    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    public EventActivity() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_event, container, false);






                final ActionBar actionBar =((AppCompatActivity)getActivity()).getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setTitle(null);

                compactCalendar = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
                compactCalendar.setUseThreeLetterAbbreviation(true);

                //Set an event for Teachers' Professional Day 2016 which is 21st of October

                Event ev1 = new Event(Color.RED, 1477040400000L, "Teachers' Professional Day");
                compactCalendar.addEvent(ev1);

                compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                    @Override
                    public void onDayClick(Date dateClicked) {
                        Context context = getContext();

                        if (dateClicked.toString().compareTo("Fri Dec 21 00:00:00 AST 2017") == 0) {
                            Toast.makeText(context, "Teachers' Professional Day", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "No Events Planned for that day", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onMonthScroll(Date firstDayOfNewMonth) {
                        actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
                    }
                });

        compactCalendar.setFirstDayOfWeek(Calendar.MONDAY);

        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
        Event ev1l = new Event(Color.GREEN, 1433701251000L, "Some extra data that I want to store.");
        compactCalendar.addEvent(ev1l);

        // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT
        Event ev2 = new Event(Color.GREEN, 1433704251000L);
        compactCalendar.addEvent(ev2);

        // Query for events on Sun, 07 Jun 2015 GMT.
        // Time is not relevant when querying for events, since events are returned by day.
        // So you can pass in any arbitary DateTime and you will receive all events for that day.
        List<Event> events = compactCalendar.getEvents(1433701251000L); // can also take a Date object

        // events has size 2 with the 2 events inserted previously
        Log.d(TAG, "Events: " + events);

        // define a listener to receive callbacks when certain events happen.
        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendar.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });



//
//    }
//        EventPojo event=new EventPojo();
//CalendarView calendarView =view.findViewById(R.id.eventCalendar);
//
//
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
//
//               EventPojo event=new EventPojo();
//
//               GregorianCalendar c = new GregorianCalendar( year, month, dayOfMonth+1 );
//
//
//                    if(count ==2) {
//                        Date newDate = new Date(String.valueOf(c.getTime()));
//                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
//                        String changeDate = sdf.format(newDate);
//                        Toast.makeText(getActivity(), "" + changeDate, Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getActivity(), NewEventActivity.class);
//                        event.setStartDate(changeDate);
//
//                        intent.putExtra("select", event);
//                        startActivity(intent);
//
//                    }else {
//                        count++;
//                    }
//
//
//
//
//
//
//            }
//
//        });
//        count=0;

return view;
    }
}

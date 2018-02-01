package com.example.admin.christembassyvallage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.admin.christembassyvallage.Adapters.AnnouncementAdapter;
import com.example.admin.christembassyvallage.model.Announcement;
import com.example.admin.christembassyvallage.model.EventPojo;
import com.example.admin.christembassyvallage.model.MyEventDay;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class AllAnnoumentActivity extends Fragment {
    private List<Announcement> announcements;
    private RecyclerView tvAnnouncement;
    private AnnouncementAdapter adapter;
    private LinearLayoutManager layoutManager;
   private String TAG;
    private int count =1;
    private   View view;
    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    public static final String RESULT = "result";
    public static final String EVENT = "event";
    private static final int ADD_NOTE = 44;
    private CalendarView mCalendarView;
    private List<EventDay> mEventDays = new ArrayList<>();

    public AllAnnoumentActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.activity_all_annoument, container, false);
        DatabaseReference databaseCatalogg = FirebaseDatabase.getInstance().getReference("Announcement");

        announcements = new ArrayList<>();


        //

        mCalendarView = view.findViewById(R.id.calendarView);
        FloatingActionButton floatingActionButton =view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                previewNote(eventDay);
            }
        });




        final ActionBar actionBar =((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

//        compactCalendar = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
//        compactCalendar.setUseThreeLetterAbbreviation(true);
//
//        //Set an event for Teachers' Professional Day 2016 which is 21st of October
//
//        Event ev1 = new Event(Color.RED, 1477040400000L, "Teachers' Professional Day");
//        compactCalendar.addEvent(ev1);
//
//        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
//            @Override
//            public void onDayClick(Date dateClicked) {
//                Context context = getContext();
//
//                if (dateClicked.toString().compareTo("Fri Dec 21 00:00:00 AST 2017") == 0) {
//                    Toast.makeText(context, "Teachers' Professional Day", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(context, "No Events Planned for that day", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onMonthScroll(Date firstDayOfNewMonth) {
//                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
//            }
        //});
//
//        compactCalendar.setFirstDayOfWeek(Calendar.MONDAY);
//
//        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
//        Event ev1l = new Event(Color.GREEN, 1433701251000L, "Some extra data that I want to store.");
//        compactCalendar.addEvent(ev1l);
//
//        // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT
//        Event ev2 = new Event(Color.GREEN, 1433704251000L);
//        compactCalendar.addEvent(ev2);
//
//        // Query for events on Sun, 07 Jun 2015 GMT.
//        // Time is not relevant when querying for events, since events are returned by day.
//        // So you can pass in any arbitary DateTime and you will receive all events for that day.
//        List<Event> events = compactCalendar.getEvents(1433701251000L); // can also take a Date object
//
//        // events has size 2 with the 2 events inserted previously
//        Log.d(TAG, "Events: " + events);
//
//        // define a listener to receive callbacks when certain events happen.
//        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
//            @Override
//            public void onDayClick(Date dateClicked) {
//                List<Event> events = compactCalendar.getEvents(dateClicked);
//                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
//            }
//
//            @Override
//            public void onMonthScroll(Date firstDayOfNewMonth) {
//                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
//            }
//        });

//        //search
//        mSearchView = (SearchView) findViewById(R.id.svFurniture);
        DatabaseReference dataS = FirebaseDatabase.getInstance().getReference("Event");

        dataS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
try {
    EventPojo eventPojo = catalogSnapshot.getValue(EventPojo.class);
    String day = eventPojo.getDay();
    String[] temp = day.split("\\s+");
//
    System.out.println("dates " + temp[0] + " " + temp[2]);

//                        Calendar cal = Calendar.getInstance();
//                        cal.set(Calendar.YEAR,Integer.parseInt(temp[0]));
//                        cal.set(Calendar.MONTH,Integer.parseInt(temp[1]));
//                        cal.set(Calendar.DAY_OF_MONTH,Integer.parseInt(temp[2]));
//
//                        MyEventDay eventDay = new MyEventDay(cal, R.drawable.briefcase, eventPojo.getNote());
//
//
//
//
//                        mCalendarView.setDate(eventDay.getCalendar());
//                        mEventDays.add(eventDay);
//                        mCalendarView.setEvents(mEventDays);
}
catch (Exception c)
{
    c.printStackTrace();
}




                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseCatalogg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                announcements.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    Announcement catalog = catalogSnapshot.getValue(Announcement.class);
                    tvAnnouncement = view.findViewById(R.id.tvAnnouncement);



                    announcements.add(catalog);
                    layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    adapter = new AnnouncementAdapter(getActivity(), announcements);

//                    Toast.makeText(CatalogActivity.this, ""+catalog.getCatalogtitle(), Toast.LENGTH_SHORT).show();
                    tvAnnouncement.setLayoutManager(layoutManager);
//                    search(mSearchView);
                    tvAnnouncement.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_NOTE && resultCode == RESULT_OK) {
            Toast.makeText(getActivity(), ""+RESULT_OK, Toast.LENGTH_SHORT).show();
            MyEventDay myEventDay = data.getParcelableExtra(RESULT);
            mCalendarView.setDate(myEventDay.getCalendar());
            mEventDays.add(myEventDay);
            mCalendarView.setEvents(mEventDays);
        }
    }
    private void addNote() {
        Intent intent = new Intent(getActivity(), AddNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE);
    }
    private void previewNote(EventDay eventDay) {
        Intent intent = new Intent(getActivity(), NotePreviewActivity.class);
        if(eventDay instanceof MyEventDay){
            intent.putExtra(EVENT, (MyEventDay) eventDay);
        }
        startActivity(intent);
    }
}

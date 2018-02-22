package com.example.admin.christembassyvallage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.admin.christembassyvallage.Adapters.EventAdpter;
import com.example.admin.christembassyvallage.model.EventPojo;
import com.example.admin.christembassyvallage.model.MyEventDay;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class AllAnnoumentActivity extends Fragment {
    private List<EventPojo> eventPojos;
    private RecyclerView tvAnnouncement;
    private EventAdpter adapter;
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
    private FloatingActionButton floatingActionButton;

    private List<EventDay> mEventDays = new ArrayList<>();

    public AllAnnoumentActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.activity_all_annoument, container, false);
        DatabaseReference databaseCatalogg = FirebaseDatabase.getInstance().getReference("Announcement");
        mCalendarView = view.findViewById(R.id.calendarView);

        tvAnnouncement = view.findViewById(R.id.tvAnnouncement);
        floatingActionButton=view.findViewById(R.id.floatingActionButton);

        eventPojos = new ArrayList<>();


        DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("Event");

        databaseItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventPojos.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    EventPojo eventPojo = (EventPojo)data.getValue(EventPojo.class);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                    String dateInString = eventPojo.getEnddate();

                    eventPojos.add(eventPojo);


                    layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);


                    adapter = new EventAdpter(getActivity(), eventPojos);

//
                    tvAnnouncement.setLayoutManager(layoutManager);
//                    search(mSearchView);
                    tvAnnouncement.setAdapter(adapter);

//
                    try {
                        Date date = sdf.parse(dateInString);

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        MyEventDay myEventDay = new MyEventDay(calendar,
                                R.drawable.datebot, eventPojo.getNote());

                        mCalendarView.setDate(myEventDay.getCalendar());
                        mEventDays.add(myEventDay);
                        mCalendarView.setEvents(mEventDays);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),databaseError.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });
//        databaseCatalogg.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                announcements.clear();
//                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
//                    Announcement catalog = catalogSnapshot.getValue(Announcement.class);
//
//
//
//
//                    announcements.add(catalog);
//                    layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//                    adapter = new AnnouncementAdapter(getActivity(), announcements);
//
////                    Toast.makeText(CatalogActivity.this, ""+catalog.getCatalogtitle(), Toast.LENGTH_SHORT).show();
//                    tvAnnouncement.setLayoutManager(layoutManager);
////                    search(mSearchView);
//                    tvAnnouncement.setAdapter(adapter);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
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

    //Convert Date to Calendar
    private Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }
}

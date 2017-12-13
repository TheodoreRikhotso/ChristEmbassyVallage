package com.example.admin.christembassyvallage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.christembassyvallage.Adapters.AnnouncementAdapter;
import com.example.admin.christembassyvallage.model.Announcement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllAnnoumentActivity extends Fragment {
    private List<Announcement> announcements;
    private RecyclerView tvAnnouncement;
    private AnnouncementAdapter adapter;
    private LinearLayoutManager layoutManager;
    private   View view;

    public AllAnnoumentActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.activity_all_annoument, container, false);
        DatabaseReference databaseCatalogg = FirebaseDatabase.getInstance().getReference("Announcement");

        announcements = new ArrayList<>();

//        //search
//        mSearchView = (SearchView) findViewById(R.id.svFurniture);

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
}

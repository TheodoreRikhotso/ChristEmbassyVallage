package com.example.admin.christembassyvallage;

import android.app.Dialog;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.christembassyvallage.Adapters.TestmonyAdapter;
import com.example.admin.christembassyvallage.model.Person;
import com.example.admin.christembassyvallage.model.Testimony;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TesimonyActivity extends Fragment {

    private View view;
    private Button btnAddTestmony;
    private FloatingActionButton floatingActionButton;
    private Dialog builder;
    private EditText etTestmony;

    private DatabaseReference db;
    private DatabaseReference db_Child;
    private DatabaseReference dbTestimony;
    private List<Testimony> testimonies;
    private RecyclerView rvAllTestimony;
    private TestmonyAdapter testmonyAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_tesimony, container, false);
        getTestimony();
        floatingActionButton =
                view.findViewById(R.id.btnAddPostTestimon);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTestimonyDialog();
            }
        });



        return view;
    }

    public  void getTestimony()
    {
        db = FirebaseDatabase.getInstance().getReference("Testimony");

        testimonies = new ArrayList<>();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                testimonies.clear();
                for (DataSnapshot catalogSnapshot : dataSnapshot.getChildren()) {
                    Testimony catalog = catalogSnapshot.getValue(Testimony.class);
                    rvAllTestimony = view.findViewById(R.id.rvAllTestimony);

                    testimonies.add(catalog);
                    layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    testmonyAdapter = new TestmonyAdapter(getActivity(), testimonies);

//                    Toast.makeText(CatalogActivity.this, ""+catalog.getCatalogtitle(), Toast.LENGTH_SHORT).show();
                    rvAllTestimony.setLayoutManager(layoutManager);
//                    search(mSearchView);
                    rvAllTestimony.setAdapter(testmonyAdapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void addTestimonyDialog() {
        builder = new Dialog(getContext());
        builder.setTitle(" Add Video");
        builder.setContentView(R.layout.add_testmony);


        etTestmony = builder.findViewById(R.id.etTestmony);
        btnAddTestmony = builder.findViewById(R.id.btnPost);


        db = FirebaseDatabase.getInstance().getReference("Profile");
        dbTestimony = FirebaseDatabase.getInstance().getReference("Testimony");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        db_Child = db.child(user.getUid());
        btnAddTestmony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_Child.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot != null) {
                            Person person = dataSnapshot.getValue(Person.class);
                            if (person != null) {
                                Calendar cal = Calendar.getInstance();
                                final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");

                                final Date currentTimes = Calendar.getInstance().getTime();

                                DateFormat date = new SimpleDateFormat("HH:mm a");



                                String test = etTestmony.getText().toString();
                                Testimony testimony = new Testimony();

                                testimony.setDate(dateFormat.format(cal.getTime()));
                                testimony.setTime(date.format(currentTimes));
                                testimony.setDescripion(test);
                                testimony.setName(person.getName() + " " + person.getSurname());
                                testimony.setUrlImage(person.getImageUrl());

                                String id = dbTestimony.push().getKey();
                                dbTestimony.child(id).setValue(testimony);



                                builder.dismiss();
                            }
                        }

//
//    }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


        builder.getWindow().getAttributes();
        builder.show();
    }


}

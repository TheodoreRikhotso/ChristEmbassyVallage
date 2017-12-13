package com.example.admin.christembassyvallage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.admin.christembassyvallage.model.Person;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
     private TextView tvName,tvEmail;
    public static   String name,surname,contact,imageUrl;
    private CircleImageView civProfileImage;

    //views
    private LinearLayout llEvent,llAnnouncement;

    //firebase
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//call view
        llEvent= (LinearLayout)findViewById(R.id.llEvent);
        llAnnouncement= (LinearLayout)findViewById(R.id.llAnnouncement);

        llEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this,EventActivity.class);
                startActivity(intent);
            }
        });

        llAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this,AnnouncementActivity.class);
                startActivity(intent);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View v = navigationView.getHeaderView(0);
         tvName = (TextView ) v.findViewById(R.id.tvName);
        tvEmail = (TextView ) v.findViewById(R.id.tvEmail);
        tvEmail.setText(mAuth.getCurrentUser().getEmail());
        civProfileImage =v.findViewById(R.id.civProfileImage);

        navigationView.setNavigationItemSelectedListener(this);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Profile");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference db_Child= db.child(user.getUid());

        db_Child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    Person person = dataSnapshot.getValue(Person.class);
                    if (person != null) {


                        name = person.getName();
                        surname = person.getSurname();
                        contact = person.getContacts();

                        tvName.setText(name+" "+surname);


//
//
//
                        Glide.with(LandingActivity.this).load(person.getImageUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(civProfileImage) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(LandingActivity.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                civProfileImage.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                    }

                }
                civProfileImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LandingActivity.this, "+"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(final int keyCode, KeyEvent event)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(LandingActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Do you wanna logout");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        if ((keyCode == KeyEvent.KEYCODE_BACK))
                        {


                            finish();
                        }
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if ((keyCode == KeyEvent.KEYCODE_BACK))
                        {


                            finish();
                        }
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

        return super.onKeyDown(keyCode, event);
    }
}

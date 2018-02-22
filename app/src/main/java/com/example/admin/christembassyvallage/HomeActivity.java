package com.example.admin.christembassyvallage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

public class HomeActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    private TextView mTextMessage;
    private TextView tvName,tvEmail;
    public static   String name,surname,contact,imageUrl;
    private CircleImageView civProfileImage;
    private FirebaseAuth mAuth;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private    Toolbar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            fragmentManager = getSupportFragmentManager();
            fragment =new AllAnnoumentActivity();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    toolbar.setTitle("Events");
                    fragment =new AllAnnoumentActivity();
                    break;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_notifications);
                    fragment =new TesimonyActivity();
                    toolbar.setTitle("Testmonies");

                    break;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_dashboard);
                    fragment =new VideosActivity();
                    toolbar.setTitle("Videos");
                    break;
                case R.id.navigation_event:
                    mTextMessage.setText(R.string.title_notifications);
                    toolbar.setTitle("Testmonies");

                    fragment =new EventActivity();


                    break;

            }


            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content, fragment).commit();
            return true;

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);


         toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Events");
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mAuth = FirebaseAuth.getInstance();

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
                        Glide.with(HomeActivity.this).load(person.getImageUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(civProfileImage) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(HomeActivity.this.getResources(), resource);
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
                Toast.makeText(HomeActivity.this, "+"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        AllAnnoumentActivity f = new AllAnnoumentActivity();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.content,f);
        transaction.commit();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
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
            Intent intent= new Intent(this,AnnouncementActivity.class);
            startActivity(intent);
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
            Toast.makeText(this, "t", Toast.LENGTH_SHORT).show();
        }  else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, SignUpForBaptismActivity.class);
            startActivity(intent);
            Toast.makeText(this, "t", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(this, ChurchLocationActivity.class);
            startActivity(intent);
            Toast.makeText(this, "t", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_location) {
            Intent intent = new Intent(this, ChurchLocationActivity.class);
            startActivity(intent);
            Toast.makeText(this, "t", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(final int keyCode, KeyEvent event)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
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

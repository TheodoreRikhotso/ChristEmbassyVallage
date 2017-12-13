package com.example.admin.christembassyvallage;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.admin.christembassyvallage.model.Person;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfileActivity extends AppCompatActivity {

   private CollapsingToolbarLayout collapsingToolbarLayout =null;
    private String name,contact,surname,email,title,role,imageUrl;
    private ImageView ivProfile;
    private TextView tvProfileName,tvProfileTitle,tvProfileContact,tvProfileEmail,tvProfileRole;
    private DatabaseReference db_Child;


    //edit profile
    private Dialog builder;
    private ImageView ivEdit,ivEditProfile;
    private EditText tvEditProfileName,tvEditProfileTitle,tvEditProfileContact,tvEditProfileEmail,tvEditProfileRole,tvEditProfileSurname;
    private  String[] titles = { "Title","MR", "MRS", "Pastor", "MISS", "Other"  };
    private  String[] roles = { "Role","Counseling","Usher", " Sundays School Teacher"};

    private int rolePosition,tilesPosition;
    private Spinner spRole,spTitle;
    private String roleSelect,titleSelect;
    private Button btnSubmit;
    private  String isImage ="1";
    private  Uri filePath;
    private StorageReference mStorageReference;
    private Uri uri;
    private ProgressDialog pd;
    private  String profileName,profileTitle,profileContact,profileEmail,profileRole,profileSurname;
    private Person person;
    private DatabaseReference db;

    //SPINNER IF INSIVE
        private boolean roleV =false;
    private boolean titleV =false;



    private int PICK_IMAGE_REQUEST = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar tb =(Toolbar)findViewById(R.id.tbProfile);
        setSupportActionBar(tb);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ivProfile =(ImageView)findViewById(R.id.ivProfile);

        tvProfileName = (TextView) findViewById(R.id.tvProfileName);
        tvProfileContact =(TextView) findViewById(R.id.tvProfileContacts);
        tvProfileEmail =(TextView) findViewById(R.id.tvProfileEmail);
        tvProfileTitle =(TextView) findViewById(R.id.tvProfileTitle);
        tvProfileRole =(TextView) findViewById(R.id.tvProfileRole);
        ivEdit =(ImageView) findViewById(R.id.ivEdit);











        collapsingToolbarLayout =(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setTitle("Profile");


         db = FirebaseDatabase.getInstance().getReference("Profile");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

         db_Child= db.child(user.getUid());

        db_Child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    Person person = dataSnapshot.getValue(Person.class);
                    if (person != null) {

                        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Please Don't Take My Man - TTF.ttf");


                        name = person.getName();
                        surname = person.getSurname();
                        contact = person.getContacts();
                        email = person.getEmail();
                        title = person.getTitle();
                        role = person.getRole();
                        imageUrl = person.getImageUrl();
                        rolePosition =person.getRolePosition();
                        tilesPosition =person.getTitlePosition();

                        tvProfileName.setText(name + " " + surname);

                        tvProfileName.setTypeface(custom_font);
                        tvProfileTitle.setText(title);
                        tvProfileEmail.setText(email);
                        tvProfileContact.setText(contact);
                        tvProfileRole.setText(role);

                        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLUE);
                        collapsingToolbarLayout.setContentScrimColor(Color.DKGRAY);


//
//
//GlideApp

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && ProfileActivity.this.isDestroyed()) {
                            return;
                        } else {
                            Glide.with(ProfileActivity.this).load(person.getImageUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(ivProfile) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable =
                                            RoundedBitmapDrawableFactory.create(ProfileActivity.this.getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    ivProfile.setImageDrawable(circularBitmapDrawable);
                                }
                            });
                        }
                    }

                }
//                civProfileImage.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
//                        startActivity(intent);
//                    }
//                });

                ivEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buildDialog(R.anim.slide_up);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "+"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        mStorageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");


    }


    private void buildDialog(int animationSource) {
         person = new Person();
        builder = new Dialog(this);

        builder.setTitle("Edit Profile");
        builder.setContentView(R.layout.edit_profile);
        //builder.setNegativeButton("OK", null);

        tvEditProfileName =builder.findViewById(R.id.tvEditProfileName);
        tvEditProfileEmail =builder.findViewById(R.id.tvEditProfileEmail);
        tvEditProfileContact = builder.findViewById(R.id.tvEditProfileContacts);
        tvEditProfileSurname = builder.findViewById(R.id.tvEditProfileSurname);

        btnSubmit =builder.findViewById(R.id.btnSubmitProfile);

        ivEditProfile =builder.findViewById(R.id.ivEditProfile);



        tvEditProfileName.setText(name);
        tvEditProfileEmail.setText(email);

        tvEditProfileContact.setText(contact);
        tvEditProfileSurname.setText(surname);



                //edit profile spinner
        spRole =builder.findViewById(R.id.spEditProfileRole);





        ArrayAdapter roleAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,roles);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spRole.post(new Runnable() {
            @Override
            public void run() {
                spRole.setSelection(rolePosition);
            }
        });
        spRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                roleSelect =roles[i];
                rolePosition =i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spRole.setAdapter(roleAdapter);


        //
        spTitle = builder.findViewById(R.id.spEditProfileTitle);


        final ArrayAdapter titleAdapter  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,titles);
        titleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner


        spTitle.post(new Runnable() {
            @Override
            public void run() {
                spTitle.setSelection(tilesPosition);
            }
        });
        spTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                titleSelect =titles[i];
               tilesPosition =i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spTitle.setAdapter(titleAdapter);




//        tvEditProfileTitle.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                titleV =2;
//
//
//                spTitle.setVisibility(View.VISIBLE);
//                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                tvEditProfileTitle.setVisibility(View.GONE);
//                return false;
//            }
//        });
//
//        tvEditProfileRole.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//            roleV =2;
//                spRole.setVisibility(View.VISIBLE);
//                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                tvEditProfileRole.setVisibility(View.GONE);
//                return false;
//            }
//        });








        Glide.with(ProfileActivity.this).load(imageUrl).asBitmap().centerCrop().into(new BitmapImageViewTarget(ivEditProfile) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(ProfileActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                ivEditProfile.setImageDrawable(circularBitmapDrawable);
            }
        });




        ivEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImage="2";
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd.show();

                profileName =tvEditProfileName.getText().toString();
                profileEmail  =tvEditProfileEmail.getText().toString();
                profileContact =tvEditProfileContact.getText().toString();
                profileSurname  =tvEditProfileSurname.getText().toString();



                if(isImage=="2") {
                    StorageReference childRef = mStorageReference.child("ProfileImage").child(filePath.getLastPathSegment());;
                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(filePath);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            @SuppressWarnings("VisibleForTests") Uri uir = taskSnapshot.getDownloadUrl();
                            Toast.makeText(ProfileActivity.this, taskSnapshot.getDownloadUrl()+" N  "+uir.toString(), Toast.LENGTH_SHORT).show();

                            person.setImageUrl(uir.toString());
                            person.setEmail(profileEmail);
                            person.setName(profileName);
                            person.setContacts(profileContact);



                                person.setTitle(titleSelect);

                        person.setRole(roleSelect);
                            person.setRolePosition(rolePosition);
                            person.setTitlePosition(tilesPosition);


                            FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
                            db.child(users.getUid()).setValue(person);

                            Toast.makeText(ProfileActivity.this, "Upload successful ", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(ProfileActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {

                    Toast.makeText(ProfileActivity.this, "Other" , Toast.LENGTH_SHORT).show();
                    person.setName(profileName);
                    person.setEmail(profileEmail);
                    person.setSurname(profileSurname);
                    person.setContacts(profileContact);
                    person.setImageUrl(imageUrl);


                    person.setTitle(titleSelect);

                    person.setRole(roleSelect);
                    person.setRolePosition(rolePosition);
                    person.setTitlePosition(tilesPosition);

                    pd.dismiss();

                    FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
                    db.child(users.getUid()).setValue(person);
                }



                builder.dismiss();


            }
        });





        builder.getWindow().getAttributes().windowAnimations = animationSource;
        builder.show();
        builder.show();
        Window window = builder.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();


            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                ivEditProfile.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

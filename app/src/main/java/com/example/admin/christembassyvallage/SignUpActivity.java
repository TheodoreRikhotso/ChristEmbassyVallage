package com.example.admin.christembassyvallage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.christembassyvallage.model.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private Button btnSign;
    private EditText etSignEmail,etSignPassword,etName,etSurname,etContact,etRenterPass;
    //private TextView loginForgotPassword,tvSign;
    private RelativeLayout activity_sign;



    //spinner
    private  String[] title = { "Title","MR", "MRS", "Pastor", "MISS", "Other"  };
    private Spinner spTitle;
    private int titlePosition;
    private String titleSelect;

    //firebase
    private FirebaseAuth mAuth;
    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSign =(Button)findViewById(R.id.btnSign);
        etSignEmail =(EditText)findViewById(R.id.etSignEmail);
        etName =(EditText)findViewById(R.id.etName);
        etContact =(EditText)findViewById(R.id.etContact);
        etRenterPass =(EditText) findViewById(R.id.etRenterPass);
        etSurname =(EditText) findViewById(R.id.etSurname);



        etSignPassword=(EditText)findViewById(R.id.etSignPassword);
        activity_sign=(RelativeLayout)findViewById(R.id.activity_sign) ;

        //firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("Profile");

        //spinner
        spTitle = (Spinner) findViewById(R.id.spTitle);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,title);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner

        spTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                titleSelect =title[i];
                titlePosition=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spTitle.setAdapter(aa);




        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String surname =etSurname.getText().toString();
                String contact = etContact.getText().toString();
                String email =etSignEmail.getText().toString();
                String password =etSignPassword.getText().toString();

                Toast.makeText(getApplicationContext(),"klm", Toast.LENGTH_SHORT).show();
                signUpUser(email,password,name,surname,contact);
            }
        });




    }

//    @Override
//    public void onClick(View view) {
//        if (view.getId() == R.id.btnSign)
//        {
//            Toast.makeText(this,"klm", Toast.LENGTH_SHORT).show();
//            signUpUser(etSignEmail.getText().toString(),etSignPassword.getText().toString());
//        }
//    }

    private void signUpUser(final String email, final String password, final String name, final String surname, final String contact)
    {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            String id =mAuth.getCurrentUser().getUid();
                            Person p = new Person();
                            p.setId(id);
                            p.setName(name);
                            p.setEmail(email);
                            p.setContacts(contact);
                            p.setPassword(password);
                            p.setSurname(surname);
                            p.setTitle(titleSelect);


                           db.child(id).setValue(p);
                            Toast.makeText(getApplicationContext(),"klm jj", Toast.LENGTH_SHORT).show();
                                Snackbar snackbar = Snackbar.make(activity_sign,"Register success",Snackbar.LENGTH_LONG);
                                snackbar.show();

                        }else {
                            Toast.makeText(getApplicationContext(),"klm non"+task.getException(), Toast.LENGTH_SHORT).show();
                            Snackbar snackbar = Snackbar.make(activity_sign,"Error "+task.getException(),Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                });

    }
}

package com.example.admin.christembassyvallage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private EditText etLoginEmail,etLoginPassword;
    private TextView loginForgotPassword,tvSign;
    private RelativeLayout activity_main;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin =(Button)findViewById(R.id.btnLogin);
        etLoginEmail =(EditText)findViewById(R.id.etLoginEmail);
        etLoginPassword=(EditText)findViewById(R.id.etLoginPassword);
        loginForgotPassword =(TextView)findViewById(R.id.loginForgotPassword);
        tvSign=(TextView)findViewById(R.id.tvSign);

        activity_main=(RelativeLayout)findViewById(R.id.activity_main) ;

        btnLogin.setOnClickListener(this);
        tvSign.setOnClickListener(this);
        loginForgotPassword.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


        ///cheeck Firebase
        if(mAuth.getCurrentUser() !=null)
        {
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }




    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.loginForgotPassword)
        {
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
        } else
            if (view.getId() == R.id.tvSign)
        {
            startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            finish();

        }else if (view.getId() == R.id.btnLogin)
        {
            if(etLoginPassword.getText().toString().isEmpty()) {
                etLoginPassword.setError("Password is required!!");

            }else {
                if(etLoginEmail.getText().toString().isEmpty()) {
                    etLoginEmail.setError("Email is required!!");
                }else {
                    loinUser(etLoginEmail.getText().toString(), etLoginPassword.getText().toString());
                }

            }



        }
    }

    private void loinUser(String email, final String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            if(password.length()<6)
                            {
                                Snackbar snackbar = Snackbar.make(activity_main,"Password Length must be o very 6",Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                            Snackbar snackbar = Snackbar.make(activity_main,"Success ",Snackbar.LENGTH_LONG);
                            snackbar.show();
                            startActivity(new Intent(LoginActivity.this,LandingActivity.class));
                            finish();
                        }else {
                            Snackbar snackbar = Snackbar.make(activity_main,"Incorrect Login "+task.getException(),Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                });

    }



}

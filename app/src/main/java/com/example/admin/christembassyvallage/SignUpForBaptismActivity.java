package com.example.admin.christembassyvallage;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class SignUpForBaptismActivity extends AppCompatActivity {

    private Toolbar toolbarSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_for_baptism);
        toolbarSignUp = (Toolbar) findViewById(R.id.toolbarSignUp);
        toolbarSignUp.setTitle("Events");
        toolbarSignUp.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbarSignUp);

    }
}

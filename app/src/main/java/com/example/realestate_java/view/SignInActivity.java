package com.example.realestate_java.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.realestate_java.R;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getSupportActionBar().hide();
    }
}
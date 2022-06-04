package com.example.realestate_java.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.realestate_java.R;
import com.example.realestate_java.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backToLogin.setOnClickListener(view -> {
            finish();
        });
    }
}
package com.example.realestate_java.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.realestate_java.R;
import com.example.realestate_java.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, FragmentContainerActivity.class));
                    finish();
                }
            }, 1000);
        } else {
            binding.signIn.setOnClickListener(view -> {
                startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                finish();
            });
        }


    }
}
package com.example.realestate_java.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AnimationUtils;

import com.example.realestate_java.R;
import com.example.realestate_java.databinding.ActivityCreateNewAddBinding;

public class CreateNewAddActivity extends AppCompatActivity {

    private ActivityCreateNewAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNewAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.crateNewAddToolBar);

        //binding.mainLayout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));

    }

    @Override
    public void finish() {
        super.finish();

        //overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
    }
}
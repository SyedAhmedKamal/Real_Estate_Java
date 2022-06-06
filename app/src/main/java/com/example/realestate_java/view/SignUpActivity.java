package com.example.realestate_java.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.realestate_java.R;
import com.example.realestate_java.databinding.ActivitySignUpBinding;
import com.example.realestate_java.model.User;
import com.example.realestate_java.viewmodel.AuthViewModel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private AuthViewModel authViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.registrationBtn.setOnClickListener(view -> {

            String email = binding.edUsernameTxtRes.getText().toString().trim();
            String password = binding.edPasswordTxtRes.getText().toString().trim();
            String c_password = binding.edConfirmPassTxtRes.getText().toString().trim();
            String name = binding.edNameTxtRes.getText().toString().trim();
            String phone = binding.edPhoneTxtRes.getText().toString().trim();
            String timeStamp = (new SimpleDateFormat("ddMMyyyyhhmmss")).format(new Date());

            if (email.isEmpty()) {
                binding.edUsernameRes.setError("Required");
            } else if (password.isEmpty()) {
                binding.edPasswordRes.setError("Required");
            } else if (c_password.isEmpty()) {
                binding.edConfirmPassRes.setError("Required");
            } else if (name.isEmpty()) {
                binding.edUsernameRes.setError("Required");
            } else if (phone.isEmpty()) {
                binding.edPhoneRes.setError("Required");
            } else {
                authViewModel.signUp(name, email, password, phone, timeStamp)
                        .observe(this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean result) {
                                if (result) {
                                    startActivity(new Intent(SignUpActivity.this, FragmentContainerActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Error occur", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });

        binding.backToLogin.setOnClickListener(view -> {
            finish();
        });
    }
}
package com.example.realestate_java.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.realestate_java.R;
import com.example.realestate_java.databinding.ActivitySignInBinding;
import com.example.realestate_java.viewmodel.AuthViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.logInBtn.setOnClickListener(view -> {
            String email = binding.edUsernameTxt.getText().toString().trim();
            String password = binding.edPasswordTxt.getText().toString().trim();

            if (email.isEmpty()) {
                binding.edUsername.setError("Email cannot be empty");
            } else if (password.isEmpty()) {
                binding.edPassword.setError("Password cannot be empty");
            } else {
                authViewModel.signIn(email, password).observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean result) {
                        if (result) {
                            Toast.makeText(SignInActivity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignInActivity.this, FragmentContainerActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(SignInActivity.this, "Sign In error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });


        binding.signUpBtn.setOnClickListener(view -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });
    }
}
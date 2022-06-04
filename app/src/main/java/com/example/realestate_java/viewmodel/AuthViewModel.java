package com.example.realestate_java.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.realestate_java.repositories.SignInRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthViewModel extends ViewModel {

    SignInRepository signInRepository;

    @Inject
    public AuthViewModel(SignInRepository signInRepository){
        this.signInRepository = signInRepository;
    }

    public MutableLiveData<Boolean> signIn(String email, String password){
        return signInRepository.SignInNow(email, password);
    }

    public MutableLiveData<Boolean> signUp(String name, String email, String password, String phone, String timeStamp){
        return signInRepository.SignUpNow(email, password, name, phone, timeStamp);
    }

}

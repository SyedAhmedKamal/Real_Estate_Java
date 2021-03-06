package com.example.realestate_java.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.realestate_java.model.User;
import com.example.realestate_java.repositories.ProfileInfoRepo;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProfileInfoViewModel extends ViewModel {

    ProfileInfoRepo profileInfoRepo;

    @Inject
    public ProfileInfoViewModel(ProfileInfoRepo profileInfoRepo) {
        this.profileInfoRepo = profileInfoRepo;
    }

    //public ProfileInfoViewModel(){}

    public MutableLiveData<User> getProfileData(){
        return profileInfoRepo.getUserInfo();
    }
}

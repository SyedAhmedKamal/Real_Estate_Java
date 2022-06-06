package com.example.realestate_java.viewmodel;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.realestate_java.repositories.UploadProfileImageRepo;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UploadProfileImageViewModel extends ViewModel {

    UploadProfileImageRepo uploadProfileImageRepo;

    @Inject
    public UploadProfileImageViewModel(UploadProfileImageRepo uploadProfileImageRepo) {
        this.uploadProfileImageRepo = uploadProfileImageRepo;
    }

    public MutableLiveData<Boolean> uploadProfileImage(Uri imageUri, String imgExt) {
        return uploadProfileImageRepo.uploadProfileImageNow(imageUri, imgExt);
    }
}

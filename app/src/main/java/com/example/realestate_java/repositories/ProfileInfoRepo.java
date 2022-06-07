package com.example.realestate_java.repositories;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.realestate_java.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class ProfileInfoRepo {

    private static final String TAG = "ProfileInfoRepo";

    FirebaseAuth auth;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    MutableLiveData<User> userInfoMutableLiveData;

    public ProfileInfoRepo() {

        userInfoMutableLiveData = new MutableLiveData<>();

        auth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase
                .getInstance()
                .getReference("Users");

    }

    public MutableLiveData<User> getUserInfo() {

        databaseReference.child(auth.getUid()).child("ProfileInfo")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            User userData = snapshot.getValue(User.class);

                            userData.setProfileImageUrl(snapshot.child("ProfileImage").child("profileImageUrl").getValue().toString());

                            userInfoMutableLiveData.postValue(userData);
                            Log.d(TAG, "onDataChange: success - " + userData.getName());
                            Log.d(TAG, "onDataChange: success - " + userData.getProfileImageUrl());
                        } else {
                            Log.d(TAG, "onDataChange: " + snapshot.exists());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "onCancelled: " + error.getMessage());
                    }
                });
        return userInfoMutableLiveData;
    }
}

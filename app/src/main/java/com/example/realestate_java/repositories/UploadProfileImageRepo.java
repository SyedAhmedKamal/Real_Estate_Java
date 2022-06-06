package com.example.realestate_java.repositories;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.realestate_java.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UploadProfileImageRepo {

    private static final String TAG = "UploadProfileImage";

    FirebaseAuth auth;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    MutableLiveData<Boolean> resultMutableLiveData;

    public UploadProfileImageRepo() {

        resultMutableLiveData = new MutableLiveData<>();

        auth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase
                .getInstance()
                .getReference("Users");
        storageReference = FirebaseStorage
                .getInstance()
                .getReference("Users")
                .child(auth.getCurrentUser().getUid())
                .child("ProfileImage");

    }

    public MutableLiveData<Boolean> uploadProfileImageNow(Uri imageUri, String imageExtension) {

        if (imageUri != null) {

            StorageReference profileImageRef = storageReference
                    .child(System.currentTimeMillis() + "." + imageExtension);

            profileImageRef.putFile(imageUri)
                    .addOnCompleteListener(uploadTask -> {

                        if (uploadTask.isSuccessful()) {

                            uploadTask.getResult().getStorage().getDownloadUrl()
                                    .addOnCompleteListener(uri -> {

                                        if (uri.isSuccessful()) {
                                            User userImage = new User();
                                            Log.d(TAG, "uploadProfileImageNow: "+uri.getResult().toString());
                                            userImage.setProfileImageUrl(uri.getResult().toString());
                                            Log.d(TAG, "uploadProfileImageNow: ..."+userImage.getProfileImageUrl());

                                            databaseReference
                                                    .child(auth.getUid())
                                                    .child("ProfileInfo")
                                                    .child("ProfileImage")
                                                    .setValue(userImage)
                                                    .addOnCompleteListener(task -> {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "uploadProfileImageNow: success" + task.getResult());
                                                            resultMutableLiveData.postValue(true);
                                                        } else {
                                                            Log.d(TAG, "uploadProfileImageNow: exception" + task.getException());
                                                            resultMutableLiveData.postValue(false);
                                                        }
                                                    });
                                        }
                                    });
                        } else {
                            Log.d(TAG, "uploadProfileImageNow: UploadTask exception" + uploadTask.getException());
                            resultMutableLiveData.postValue(false);
                        }

                    });

        }
        return resultMutableLiveData;
    }
}

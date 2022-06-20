package com.example.realestate_java.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.realestate_java.model.Post;
import com.example.realestate_java.model.SelectImagesAdapterModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class PostRepository {

    private static final String TAG = "CreateNewAddActivity";

    FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    MutableLiveData<String> mutableLiveData;
    MutableLiveData<ArrayList<String>> postCreatedMutableLiveData;
    private static ArrayList<String> imageUrls;

    public PostRepository() {

        auth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mutableLiveData = new MutableLiveData<>();
        postCreatedMutableLiveData = new MutableLiveData<>();
        imageUrls = new ArrayList<>();

    }

    public MutableLiveData<ArrayList<String>> createPost(ArrayList<SelectImagesAdapterModel> imageList, Context context) {


        for (int i = 0; i < imageList.size(); i++) {
            ContentResolver contentResolver = context.getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String imageExtension =  mime.getExtensionFromMimeType(contentResolver.getType(imageList.get(i).getImageUri()));


            StorageReference postImgStorage = firebaseStorage
                    .getReference("Users")
                    .child(auth.getCurrentUser().getUid())
                    .child("Posts")
                    .child(System.currentTimeMillis() + "." + imageExtension);

            int finalI = i;
            postImgStorage.putFile(imageList.get(i).getImageUri())
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                task.getResult().getStorage().getDownloadUrl()
                                        .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                imageUrls.add(task.getResult().toString());
                                                Log.d(TAG, "onComplete: "+task.getResult());
                                                if (finalI ==imageList.size()-1){
                                                    postCreatedMutableLiveData.postValue(imageUrls);
                                                }
                                            }
                                        });
                            } else {
                                Log.d(TAG, "onComplete: "+task.getException());
                                mutableLiveData.postValue(null);
                            }
                        }
                    });
        }
        return postCreatedMutableLiveData;
    }

   /* public MutableLiveData<Boolean> storeDataToFirebaseDB(Post post) {

        String postID = databaseReference.push().getKey();

        databaseReference
                .child(auth.getUid())
                .child("Posts")
                .child(postID)
                .setValue(post)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            postCreatedMutableLiveData.postValue(true);
                        } else {
                            postCreatedMutableLiveData.postValue(false);
                        }
                    }
                });

        return postCreatedMutableLiveData;
    }*/


}

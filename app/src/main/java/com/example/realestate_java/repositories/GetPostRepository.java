package com.example.realestate_java.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.realestate_java.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GetPostRepository {

    private static final String TAG = "GetPostRepository";

    FirebaseAuth auth;
    DatabaseReference databaseReference;
    MutableLiveData<ArrayList<Post>> getLivePostMutableLiveData;
    ArrayList<Post> postArrayList;

    public GetPostRepository() {
        auth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase
                .getInstance()
                .getReference("Users");

        getLivePostMutableLiveData = new MutableLiveData<>();
        postArrayList = new ArrayList<>();
    }

    public MutableLiveData<ArrayList<Post>> getAllPosts(){
       databaseReference
               .addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {

                       for (DataSnapshot snapshot1:snapshot.getChildren()){
                           Log.d(TAG, "onDataChange: "+snapshot1.getValue());
                           Log.d(TAG, "onDataChange: POST KEY - "+snapshot1.child("Posts").getValue());

                           for (DataSnapshot snapshot2:snapshot1.child("Posts").getChildren()){
                               Log.d(TAG, "onDataChange: POST - "+snapshot2.getValue());

                               Post post = snapshot2.getValue(Post.class);
                               Log.d(TAG, "onDataChange: POSTS - "+post.getPostTitle());
                               postArrayList.add(post);
                               getLivePostMutableLiveData.postValue(postArrayList);

                           }
                       }

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
       return getLivePostMutableLiveData;
    }
}

package com.example.realestate_java.repositories;

import android.util.Log;
import android.widget.ArrayAdapter;

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

public class GetPostByUserRepo {

    private static final String TAG = "GetPostByUserRepo";

    FirebaseAuth auth;
    DatabaseReference databaseReference;
    MutableLiveData<ArrayList<Post>> getLivePostUserMutableLiveData;
    private static Post post;
    private static ArrayList<Post> postArrayList;

    public GetPostByUserRepo() {
        auth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase
                .getInstance()
                .getReference("Users");

        getLivePostUserMutableLiveData = new MutableLiveData<>();

        postArrayList = new ArrayList<>();
    }

    public MutableLiveData<ArrayList<Post>> getAllPosts(){
        postArrayList.clear();
        databaseReference
                .child(auth.getUid())
                .child("Posts")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            Log.d(TAG, "onDataChange: "+snapshot.getValue());
                            Log.d(TAG, "onDataChange: POST KEY - "+snapshot.getKey());

                            for (DataSnapshot snapshot2:snapshot.getChildren()){
                                Log.d(TAG, "onDataChange: POST - "+snapshot2.getKey());

                                post = snapshot2.getValue(Post.class);
                                postArrayList.add(post);
                                Log.d(TAG, "onDataChange: POSTS - "+post.getPostTitle());

                            }
                        getLivePostUserMutableLiveData.postValue(postArrayList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "onCancelled: "+error.getMessage());
                    }
                });

        return getLivePostUserMutableLiveData;
    }

}

package com.example.realestate_java.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.realestate_java.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GetSearchedPostRepo {

    private static final String TAG = "GetSearchedPostRepo";

    FirebaseAuth auth;
    DatabaseReference databaseReference;
    MutableLiveData<ArrayList<Post>> getSearchedPostsMutableLiveData;
    private static ArrayList<Post> postArrayList;

    public GetSearchedPostRepo() {
        auth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase
                .getInstance()
                .getReference("Users");

        getSearchedPostsMutableLiveData = new MutableLiveData<>();
        postArrayList = new ArrayList<>();
    }

    public MutableLiveData<ArrayList<Post>> searchedPosts(String query){

        databaseReference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                           /* Log.d(TAG, "onDataChange: "+snapshot1.getValue());
                            Log.d(TAG, "onDataChange: POST KEY - "+snapshot1.child("Posts").getValue());*/

                            for (DataSnapshot snapshot2:snapshot1.child("Posts").getChildren()){
                               /* Log.d(TAG, "onDataChange: POST - "+snapshot2.getValue());
                                */
                                Post post = snapshot2.getValue(Post.class);
                                
                                if (post.getPostCategory().equalsIgnoreCase("house") && query.equalsIgnoreCase("house")){
                                    Log.d(TAG, "onDataChange: POSTS - "+post.getPostCategory());
                                    Log.d(TAG, "onDataChange: POSTS - "+post.getPrice());
                                    postArrayList.add(post);
                                }
                                else if(post.getPostCategory().equalsIgnoreCase("shop") && query.equalsIgnoreCase("shop")){
                                    Log.d(TAG, "onDataChange: POSTS - "+post.getPostCategory());
                                    postArrayList.add(post);
                                }
                                else if(post.getPostCategory().equalsIgnoreCase("plot") && query.equalsIgnoreCase("plot")){
                                    Log.d(TAG, "onDataChange: POSTS - "+post.getPostCategory());
                                    postArrayList.add(post);
                                }
                                /*else {
                                    Log.d(TAG, "onDataChange: NO MATCH");
                                }*/

                            }
                        }
                        getSearchedPostsMutableLiveData.postValue(postArrayList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "onCancelled: "+error);
                    }
                });

        return getSearchedPostsMutableLiveData;
    }
}

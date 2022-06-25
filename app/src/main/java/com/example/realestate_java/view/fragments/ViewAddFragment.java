package com.example.realestate_java.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realestate_java.R;
import com.example.realestate_java.model.Post;

public class ViewAddFragment extends Fragment {


    private static final String TAG = "ViewAddFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Post post = ViewAddFragmentArgs.fromBundle(getArguments()).getMyPost();

        if (post != null){
            Log.d(TAG, "onViewCreated: "+post.getPostCategory());
            Log.d(TAG, "onViewCreated: "+post.getPostImagesList().get(0));
            Log.d(TAG, "onViewCreated: "+post.getLocality());
        }
    }
}
package com.example.realestate_java.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.realestate_java.model.Post;
import com.example.realestate_java.repositories.GetPostByUserRepo;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class GetPostsByUserViewModel extends ViewModel {

    GetPostByUserRepo getPostByUserRepo;

    @Inject
    public GetPostsByUserViewModel(GetPostByUserRepo getPostByUserRepo) {
        this.getPostByUserRepo = getPostByUserRepo;
    }

    public MutableLiveData<ArrayList<Post>> getPosts(){
        return getPostByUserRepo.getAllPosts();
    }
}

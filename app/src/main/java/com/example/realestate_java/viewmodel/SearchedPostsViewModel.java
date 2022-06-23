package com.example.realestate_java.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.realestate_java.model.Post;
import com.example.realestate_java.repositories.GetSearchedPostRepo;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SearchedPostsViewModel extends ViewModel {


    GetSearchedPostRepo getSearchedPostRepo;

    @Inject
    public SearchedPostsViewModel(GetSearchedPostRepo getSearchedPostRepo) {
        this.getSearchedPostRepo = getSearchedPostRepo;
    }

    public MutableLiveData<ArrayList<Post>> getSearchedPosts(String s){
        return getSearchedPostRepo.searchedPosts(s);
    }
}

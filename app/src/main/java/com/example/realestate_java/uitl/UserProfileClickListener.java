package com.example.realestate_java.uitl;

import android.view.View;

public interface UserProfileClickListener {

    void selectImage(int position);
    void logOut(int position);
    void onItemClick(int position, View view);
}

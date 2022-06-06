package com.example.realestate_java.model;

public class UserProfileAdapterModel {

    int viewType;

    String userProfileImage;
    String userName;
    String email;
    String phone;

    public UserProfileAdapterModel(int viewType, String userProfileImage, String userName, String email, String phone) {
        this.viewType = viewType;
        this.userProfileImage = userProfileImage;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }

    public int getViewType() {
        return viewType;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}

package com.example.realestate_java.model;

public class User {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String createTime;

    private String profileImageUrl;

    public User() {
    }

    public User(String name, String email, String password, String phoneNumber, String createTime) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.createTime = createTime;
    }

    public User(String name, String email, String password, String phoneNumber, String createTime, String profileImageUrl) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.createTime = createTime;
        this.profileImageUrl = profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}

package com.example.realestate_java.model;

import java.util.List;

public class Post {

    private String uid;
    private String userName;
    private String userImage;
    private String createDateTime;

    private List<String> postImagesList;
    private String postTitle;
    private String postSubtitle;
    private String postCategory;
    private String price;
    private String postLocation;
    private String postDescription;
    private String contactInfo;

    public Post() {
    }

    // with minimal necessary info
    public Post(String uid,
                String userName,
                String userImage,
                String createDateTime,
                List<String> postImagesList,
                String postTitle,
                String postCategory,
                String postDescription,
                String contactInfo) {

        this.uid = uid;
        this.userName = userName;
        this.userImage = userImage;
        this.createDateTime = createDateTime;
        this.postImagesList = postImagesList;
        this.postTitle = postTitle;
        this.postCategory = postCategory;
        this.postDescription = postDescription;
        this.contactInfo = contactInfo;
    }

    // If all info available
    public Post(String uid,
                String userName,
                String userImage,
                String createDateTime,
                List<String> postImagesList,
                String postTitle,
                String postSubtitle,
                String postCategory,
                String price,
                String postLocation,
                String postDescription,
                String contactInfo) {

        this.uid = uid;
        this.userName = userName;
        this.userImage = userImage;
        this.createDateTime = createDateTime;
        this.postImagesList = postImagesList;
        this.postTitle = postTitle;
        this.postSubtitle = postSubtitle;
        this.postCategory = postCategory;
        this.price = price;
        this.postLocation = postLocation;
        this.postDescription = postDescription;
        this.contactInfo = contactInfo;
    }

    public String getUid() {
        return uid;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public List<String> getPostImagesList() {
        return postImagesList;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostSubtitle() {
        return postSubtitle;
    }

    public String getPostCategory() {
        return postCategory;
    }

    public String getPrice() {
        return price;
    }

    public String getPostLocation() {
        return postLocation;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public String getContactInfo() {
        return contactInfo;
    }
}
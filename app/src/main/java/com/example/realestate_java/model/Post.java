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
    private String postSubCategory;
    private String price;
    private String postLocationLat;
    private String postLocationLang;
    private String postDescription;
    private String contactInfo;

    private String address;

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
                String postSubCategory,
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
        this.postSubCategory = postSubCategory;
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
                String postSubCategory,
                String price,
                String postLocationLang,
                String postLocationLat,
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
        this.postLocationLat = postLocationLat;
        this.postLocationLang = postLocationLang;
        this.postDescription = postDescription;
        this.contactInfo = contactInfo;
        this.postSubCategory = postSubCategory;
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

    public String getPostDescription() {
        return postDescription;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getPostSubCategory() {
        return postSubCategory;
    }

    public String getPostLocationLat() {
        return postLocationLat;
    }

    public String getPostLocationLang() {
        return postLocationLang;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

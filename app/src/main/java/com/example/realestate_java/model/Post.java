package com.example.realestate_java.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import kotlinx.parcelize.Parcelize;

@Parcelize
public class Post implements Parcelable {

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
    private double postLocationLat;
    private double postLocationLang;
    private String postDescription;
    private String contactInfo;

    private String address;
    private String locality;

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
                double postLocationLang,
                double postLocationLat,
                String contactInfo,
                String address,
                String locality) {

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
        this.contactInfo = contactInfo;
        this.postSubCategory = postSubCategory;
        this.address = address;
        this.locality = locality;
    }

    protected Post(Parcel in) {
        uid = uid;
        userName = in.readString();
        userImage = in.readString();
        createDateTime = in.readString();
        in.readStringList(postImagesList);
        postTitle = in.readString();
        postSubtitle = in.readString();
        postCategory = in.readString();
        price = in.readString();
        postLocationLat = in.readDouble();
        postLocationLang = in.readDouble();
        contactInfo = in.readString();
        postSubCategory = in.readString();
        locality = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

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

    public double getPostLocationLat() {
        return postLocationLat;
    }

    public double getPostLocationLang() {
        return postLocationLang;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocality() {
        return locality;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(userName);
        parcel.writeString(userImage);
        parcel.writeString(createDateTime);

        parcel.writeList(postImagesList);
        parcel.writeString(postTitle);
        parcel.writeString(postSubtitle);
        parcel.writeString(postCategory);
        parcel.writeString(postSubCategory);
        parcel.writeString(price);
        parcel.writeDouble(postLocationLat);
        parcel.writeDouble(postLocationLang);
        parcel.writeString(postDescription);
        parcel.writeString(contactInfo);
        parcel.writeString(address);
        parcel.writeString(locality);

    }
}

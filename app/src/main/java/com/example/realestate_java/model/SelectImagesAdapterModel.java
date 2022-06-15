package com.example.realestate_java.model;

import android.net.Uri;

import java.util.ArrayList;

public class SelectImagesAdapterModel {
    Uri imgUri;

    public SelectImagesAdapterModel(Uri imgUri) {
        this.imgUri = imgUri;
    }

    public Uri getUriArrayList() {
        return imgUri;
    }
}

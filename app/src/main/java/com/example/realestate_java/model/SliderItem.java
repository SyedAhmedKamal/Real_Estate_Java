package com.example.realestate_java.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SliderItem implements Parcelable {

    private String url;
    private ArrayList<SliderItem> list;

    public SliderItem(String url) {
        this.url = url;
    }

    protected SliderItem(Parcel in){
        url = in.readString();
        in.writeList(list);
    }

    public static final Creator<SliderItem> CREATOR = new Creator<SliderItem>() {
        @Override
        public SliderItem createFromParcel(Parcel in) {
            return new SliderItem(in);
        }

        @Override
        public SliderItem[] newArray(int size) {
            return new SliderItem[size];
        }
    };

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeList(list);
    }
}

package com.example.realestate_java.model;

public class SliderItem {

    private int imageID;
    private String text;

    public SliderItem(int imageID, String text) {
        this.imageID = imageID;
        this.text = text;
    }

    public int getImageID() {
        return imageID;
    }

    public String getText() {
        return text;
    }
}

package com.example.realestate_java.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realestate_java.Adapter.SliderAdapter;
import com.example.realestate_java.R;
import com.example.realestate_java.model.Post;
import com.example.realestate_java.model.SliderItem;

import java.util.ArrayList;

public class ViewImagesFragment extends Fragment {

    private static final String TAG = "ViewImagesFragment";
    private ViewPager2 viewPager2;
    private ArrayList<SliderItem> sliderItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_images, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2 = view.findViewById(R.id.viewPagerSlider);

        Post post = ViewImagesFragmentArgs.fromBundle(getArguments()).getMyPost();
        int position = ViewImagesFragmentArgs.fromBundle(getArguments()).getMyImagePosition();

        sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(post.getPostImagesList().get(position)));
        for (int i = 0; i < post.getPostImagesList().size(); i++) {
            if (!post.getPostImagesList().get(position).equals(post.getPostImagesList().get(i))){
                sliderItems.add(new SliderItem(post.getPostImagesList().get(i)));
            }
        }

        Log.d(TAG, "onViewCreated: " + sliderItems.size());
        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2, null));

    }
}
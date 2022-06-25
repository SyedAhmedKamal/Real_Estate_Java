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
import android.widget.TextView;

import com.example.realestate_java.Adapter.SliderAdapter;
import com.example.realestate_java.R;
import com.example.realestate_java.model.Post;
import com.example.realestate_java.model.SliderItem;
import com.example.realestate_java.view.CreateNewAddActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class ViewAddFragment extends Fragment {

    private static final String TAG = "ViewAddFragment";

    private ViewPager2 viewPager2;
    private TextView title, subTitle, category, subCategory, price, location, contact;
    private Post post;
    private GoogleMap mMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_add, container, false);

        // Initialize map fragment
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.set_map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;

                post = ViewAddFragmentArgs.fromBundle(getArguments()).getMyPost();

                Log.d(TAG, "onMapReady: "+post.getPostLocationLat());

                LatLng latLng = new LatLng(post.getPostLocationLat(), post.getPostLocationLang());
                mMap.addMarker(new MarkerOptions().position(latLng).title("Address"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f));
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2 = view.findViewById(R.id.viewPagerSlider);

        title = view.findViewById(R.id.tv_title);
        subTitle = view.findViewById(R.id.tv_sub_title);
        category = view.findViewById(R.id.tv_category);
        subCategory = view.findViewById(R.id.tv_subCat);
        price = view.findViewById(R.id.tv_price);
        location = view.findViewById(R.id.tv_set_loaction);
        contact = view.findViewById(R.id.tv_contact_info);

        post = ViewAddFragmentArgs.fromBundle(getArguments()).getMyPost();

        if (post != null) {
            Log.d(TAG, "onViewCreated: " + post.getPostCategory());
            Log.d(TAG, "onViewCreated: " + post.getPostImagesList().get(0));
            Log.d(TAG, "onViewCreated: " + post.getLocality());

            title.setText(post.getPostTitle());
            subTitle.setText(post.getPostSubtitle());
            category.setText(post.getPostCategory());
            subCategory.setText(post.getPostSubCategory());
            price.setText(String.format("Price: " + post.getPrice()));
            location.setText(post.getAddress());
            contact.setText(post.getContactInfo());

            ArrayList<SliderItem> sliderItems = new ArrayList<>();
            for (int i = 0; i < post.getPostImagesList().size(); i++) {
                sliderItems.add(new SliderItem(post.getPostImagesList().get(i)));
            }

            Log.d(TAG, "onViewCreated: " + sliderItems.size());
            viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
        }
    }
}
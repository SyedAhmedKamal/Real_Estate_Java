package com.example.realestate_java.view.fragments;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestate_java.Adapter.PostAdapter;
import com.example.realestate_java.Adapter.SliderAdapter;
import com.example.realestate_java.MainActivity;
import com.example.realestate_java.R;
import com.example.realestate_java.model.Post;
import com.example.realestate_java.model.SliderItem;
import com.example.realestate_java.network.NetworkStateCheck;
import com.example.realestate_java.repositories.GetPostRepository;
import com.example.realestate_java.view.FragmentContainerActivity;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private LinearLayout noInternet;
    private TextView textView;
    private Button refreshButton;
    private RecyclerView recyclerView;

    private RecyclerView postRecyclerView;
    private ArrayList<Post> postsList;

    @Inject
    NetworkStateCheck networkStateCheck;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noInternet = view.findViewById(R.id.no_internet);
        refreshButton = view.findViewById(R.id.refreshBtn);
        recyclerView = view.findViewById(R.id.slider_recyclerView);
        postRecyclerView = view.findViewById(R.id.postRecyclerView);
        postsList = new ArrayList<>();

        networkStateCheck = new NetworkStateCheck();
        requireActivity().registerReceiver(networkStateCheck, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        networkStateCheck.check.observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                Log.d(TAG, "onChanged: " + result);

                if (result) {
                    //textView.setVisibility(View.VISIBLE);
                    noInternet.setVisibility(View.GONE);
                    viewPagerList();
                    Toast.makeText(requireActivity(), "Connected", Toast.LENGTH_SHORT).show();

                    loadAllPosts();
                }
                else{
                    //textView.setVisibility(View.GONE);
                    noInternet.setVisibility(View.VISIBLE);
                    Toast.makeText(requireActivity(), "No Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        refreshButton.setOnClickListener(view1 -> {
            networkStateCheck.check.observe(requireActivity(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean result) {
                    Log.d(TAG, "onChanged: " + result);

                    if (result) {
                        //textView.setVisibility(View.VISIBLE);
                        noInternet.setVisibility(View.GONE);
                        Toast.makeText(requireActivity(), "Connected", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        textView.setVisibility(View.GONE);
                        noInternet.setVisibility(View.VISIBLE);
                        Toast.makeText(requireActivity(), "No Internet", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

    }

    private void loadAllPosts() {

        GetPostRepository getPostRepository = new GetPostRepository();
        getPostRepository.getAllPosts().observe(requireActivity(), new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> posts) {

                postsList.clear();
                postsList = posts;
                postRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                postRecyclerView.setAdapter(new PostAdapter(postsList));
            }
        });

    }

    private void viewPagerList() {
        ArrayList<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.house, "House"));
        sliderItems.add(new SliderItem(R.drawable.shop, "Shop"));
        sliderItems.add(new SliderItem(R.drawable.plot, "Plot"));

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(new SliderAdapter(sliderItems));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().unregisterReceiver(networkStateCheck);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called");
        loadAllPosts();
    }
}
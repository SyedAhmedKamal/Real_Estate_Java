package com.example.realestate_java.view.fragments;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.realestate_java.repositories.GetSearchedPostRepo;
import com.example.realestate_java.uitl.PostClickListener;
import com.example.realestate_java.view.FragmentContainerActivity;
import com.example.realestate_java.viewmodel.SearchedPostsViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, PostClickListener {

    private static final String TAG = "HomeFragment";
    private Button refreshButton;
    private RecyclerView recyclerView;

    private RecyclerView postRecyclerView;
    private ArrayList<Post> postsList;

    private SearchView searchView;
    private SearchedPostsViewModel searchedPostsViewModel;
    private LinearLayout main, noInternet, shimmerLoader;
    private ShimmerFrameLayout shimmerFrameLayout;

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
        main = view.findViewById(R.id.main_screen);
        shimmerLoader = view.findViewById(R.id.shimmer_loader);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        refreshButton = view.findViewById(R.id.refreshBtn);
        postRecyclerView = view.findViewById(R.id.postRecyclerView);
        searchView = view.findViewById(R.id.searchView);
        searchedPostsViewModel = new ViewModelProvider(requireActivity()).get(SearchedPostsViewModel.class);

        shimmerLoader.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        postsList = new ArrayList<>();

        searchView.setOnQueryTextListener(this);

        networkStateCheck = new NetworkStateCheck();
        requireActivity().registerReceiver(networkStateCheck, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        networkStateCheck.check.observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                Log.d(TAG, "onChanged: " + result);

                if (result) {
                    //textView.setVisibility(View.VISIBLE);
                    noInternet.setVisibility(View.GONE);
                    shimmerLoader.setVisibility(View.GONE);
                    main.setVisibility(View.VISIBLE);
                    Toast.makeText(requireActivity(), "Connected", Toast.LENGTH_SHORT).show();

                    loadAllPosts();
                }
                else{
                    //textView.setVisibility(View.GONE);
                    noInternet.setVisibility(View.VISIBLE);
                    shimmerLoader.setVisibility(View.GONE);
                    main.setVisibility(View.GONE);
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
                        noInternet.setVisibility(View.GONE);
                        Toast.makeText(requireActivity(), "Connected", Toast.LENGTH_SHORT).show();
                    }
                    else{
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
                postRecyclerView.setAdapter(new PostAdapter(postsList, HomeFragment.this));
            }
        });

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

    @Override
    public boolean onQueryTextSubmit(String query) {

        if (query.isEmpty()){
            /*postsList.clear();
            postRecyclerView.setAdapter(new PostAdapter(postsList));*/
            loadAllPosts();
        }
        else {
            processQuery(query);
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText.isEmpty()){
            /*postsList.clear();
            postRecyclerView.setAdapter(new PostAdapter(postsList));*/
            loadAllPosts();
        }
        else {
            processQuery(newText);
        }

        return false;
    }

    private void processQuery(String query) {
        postsList.clear();
        searchedPostsViewModel.getSearchedPosts(query).observe(getViewLifecycleOwner(), new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> posts) {
                Log.d(TAG, "onChanged: "+posts.size());
                postsList = posts;
                postRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                postRecyclerView.setAdapter(new PostAdapter(postsList, HomeFragment.this));
            }
        });
    }

    @Override
    public void onItemClick(Post post, View view) {
        NavDirections navDirections = HomeFragmentDirections.actionHomeFragmentNavToViewAddFragment(post);
        Navigation.findNavController(view).navigate(navDirections);
    }
}
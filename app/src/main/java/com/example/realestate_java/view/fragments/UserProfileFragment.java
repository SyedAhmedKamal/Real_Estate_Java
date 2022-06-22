package com.example.realestate_java.view.fragments;

import static com.example.realestate_java.Adapter.UserProfileAdapterMVT.VIEW_ONE_PROFILE;
import static com.example.realestate_java.Adapter.UserProfileAdapterMVT.VIEW_TWO_POSTS;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.realestate_java.Adapter.UserProfileAdapterMVT;
import com.example.realestate_java.R;
import com.example.realestate_java.model.Post;
import com.example.realestate_java.model.User;
import com.example.realestate_java.model.UserProfileAdapterModel;
import com.example.realestate_java.network.NetworkStateCheck;
import com.example.realestate_java.repositories.GetPostByUserRepo;
import com.example.realestate_java.uitl.UserProfileClickListener;
import com.example.realestate_java.view.SignInActivity;
import com.example.realestate_java.viewmodel.GetPostsByUserViewModel;
import com.example.realestate_java.viewmodel.ProfileInfoViewModel;
import com.example.realestate_java.viewmodel.UploadProfileImageViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import javax.inject.Inject;

public class UserProfileFragment extends Fragment implements UserProfileClickListener {

    @Inject
    NetworkStateCheck networkStateCheck;

    private static final String TAG = "UserProfileFragment";

    private RecyclerView recyclerView;
    private ArrayList<UserProfileAdapterModel> list;
    private UserProfileAdapterMVT adapter;
    private Uri imgUri;
    private LinearLayout noInternet;
    private Button refreshButton;

    private ActivityResultLauncher<String> uploadProfileImageLauncher;
    private UploadProfileImageViewModel viewModel;

    ImageView addProfileImage;
    private ProfileInfoViewModel profileInfoViewModel;

    private GetPostsByUserViewModel getPostsByUserViewModel;
    private ArrayList<Post> postsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        networkStateCheck = new NetworkStateCheck();
        requireActivity().registerReceiver(networkStateCheck, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getImageUri();

        viewModel = new ViewModelProvider(requireActivity()).get(UploadProfileImageViewModel.class);
        profileInfoViewModel = new ViewModelProvider(requireActivity()).get(ProfileInfoViewModel.class);
        getPostsByUserViewModel = new ViewModelProvider(requireActivity()).get(GetPostsByUserViewModel.class);

        refreshButton = view.findViewById(R.id.refresh_btn_pro);
        noInternet = view.findViewById(R.id.no_internet_layout);
        recyclerView = view.findViewById(R.id.recyclerViewProfile);
        addProfileImage = view.findViewById(R.id.add_profile_image_btn);

        list = new ArrayList<>();
        postsList = new ArrayList<>();
        internetCheck();

        refreshButton.setOnClickListener(view1 -> {
            internetCheck();
        });


    }

    private void internetCheck() {
        networkStateCheck.check.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean connected) {

                if (connected) {
                    noInternet.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    loadProfileInfo();
                    loadProfilePosts();
                } else {
                    noInternet.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadProfilePosts() {
        postsList.clear();
        getPostsByUserViewModel.getPosts().observe(getViewLifecycleOwner(), new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> posts) {
                postsList = posts;
                Log.d(TAG, "onChanged: "+posts.size());

                for (Post post:posts) {
                    Log.d(TAG, "onChanged: postData - "+post.getPostTitle());
                    list.add(new UserProfileAdapterModel(VIEW_TWO_POSTS, post));
                    adapter = new UserProfileAdapterMVT(list, UserProfileFragment.this);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    private void loadProfileInfo() {
        profileInfoViewModel.getProfileData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {

                Log.d(TAG, "onChanged: called");

                list.clear();

                list.add(0, new UserProfileAdapterModel(VIEW_ONE_PROFILE,
                        user.getProfileImageUrl(),
                        user.getName(),
                        String.format("Email " + user.getEmail()),
                        String.format("Phone Number " + user.getPhoneNumber())));

                adapter = new UserProfileAdapterMVT(list, UserProfileFragment.this);

                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setAdapter(adapter);
            }

        });
    }

    private void getImageUri() {

        uploadProfileImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {

            if (result != null) {
                imgUri = result;
                uploadProfileImage();
            } else {
                Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private String getImageExtension(Uri uri) {
        ContentResolver contentResolver = requireActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadProfileImage() {
        if (imgUri != null) {
            viewModel.uploadProfileImage(imgUri, getImageExtension(imgUri))
                    .observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean result) {
                            if (result) {
                                Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireActivity(), "Error occur", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void selectImage(int position) {
        uploadProfileImageLauncher.launch("image/*");
    }

    @Override
    public void logOut(int position) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(requireActivity(), SignInActivity.class));
        requireActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().unregisterReceiver(networkStateCheck);
    }
}
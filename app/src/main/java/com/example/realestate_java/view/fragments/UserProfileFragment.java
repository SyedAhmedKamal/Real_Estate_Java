package com.example.realestate_java.view.fragments;

import static com.example.realestate_java.Adapter.UserProfileAdapterMVT.VIEW_ONE_PROFILE;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.realestate_java.Adapter.UserProfileAdapterMVT;
import com.example.realestate_java.R;
import com.example.realestate_java.model.UserProfileAdapterModel;
import com.example.realestate_java.uitl.UserProfileClickListener;
import com.example.realestate_java.viewmodel.UploadProfileImageViewModel;

import java.util.ArrayList;

public class UserProfileFragment extends Fragment implements UserProfileClickListener {

    private static final String TAG = "UserProfileFragment";
    private RecyclerView recyclerView;
    private ArrayList<UserProfileAdapterModel> list;
    private UserProfileAdapterMVT adapter;
    private Uri imgUri;

    private ActivityResultLauncher<String> uploadProfileImageLauncher;
    private UploadProfileImageViewModel viewModel;

    ImageView addProfileImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(UploadProfileImageViewModel.class);
        getImageUri();

        recyclerView = view.findViewById(R.id.recyclerViewProfile);
        addProfileImage = view.findViewById(R.id.add_profile_image_btn);

        list = new ArrayList<>();
        list.add(new UserProfileAdapterModel(VIEW_ONE_PROFILE, "", "Shah", "gmail", "0312"));

        adapter = new UserProfileAdapterMVT(list, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

       /* addProfileImage.setOnClickListener(view1 -> {
            uploadProfileImageLauncher.launch("image/*");
        });*/
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
                    .observe(requireActivity(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean result) {
                            if (result){
                                Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show();
                            }
                            else{
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
}
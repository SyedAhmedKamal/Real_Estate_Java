package com.example.realestate_java.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.realestate_java.Adapter.SelectImagesAdapter;
import com.example.realestate_java.R;
import com.example.realestate_java.databinding.ActivityCreateNewAddBinding;
import com.example.realestate_java.model.SelectImagesAdapterModel;
import com.example.realestate_java.uitl.SelectImagesClickListener;

import java.util.ArrayList;
import java.util.List;

public class CreateNewAddActivity extends AppCompatActivity implements SelectImagesClickListener {

    private static final String TAG = "CreateNewAddActivity";
    private ActivityCreateNewAddBinding binding;
    private ActivityResultLauncher<Intent> activityResultLauncherImages;
    private ArrayList<SelectImagesAdapterModel> imageList;

    private SelectImagesAdapter adapter;
    private ArrayList<SelectImagesAdapterModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNewAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectImages();
        setSupportActionBar(binding.crateNewAddToolBar);

        imageList = new ArrayList<>();
        binding.selectListImageLayout.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            activityResultLauncherImages.launch(intent);
        });


        initRecyclerView();
    }

    private void initRecyclerView() {
        list = new ArrayList<>();
        if (imageList.size()==0){
           binding.selectListImageLayout.setVisibility(View.VISIBLE);
           binding.selectImagesRecyclerView.setVisibility(View.INVISIBLE);
        }
        else {
            binding.selectListImageLayout.setVisibility(View.INVISIBLE);
            binding.selectImagesRecyclerView.setVisibility(View.VISIBLE);
            adapter = new SelectImagesAdapter(imageList);
            binding.selectImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            binding.selectImagesRecyclerView.setAdapter(adapter);
        }

    }

    private void selectImages() {

        activityResultLauncherImages = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getData() != null) {
                            Log.d(TAG, "onActivityResult: Data- " + result.getData());

                            if (result.getData().getClipData() != null) {
                                int count = result.getData().getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                                imageList = new ArrayList<>(count);
                                for (int i = 0; i < count; i++) {
                                    Uri imageUri = result.getData().getClipData().getItemAt(i).getUri();
                                    imageList.add(new SelectImagesAdapterModel(imageUri));
                                    Log.d(TAG, "onActivityResult: Image - " + imageUri);
                                }
                                initRecyclerView();
                                Log.d(TAG, "onActivityResult: " + imageList.size());
                            }

                        } else {
                            Log.d(TAG, "onActivityResult: NO IMAGE SELECTED");
                        }
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.close_post) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void selectListOfImages(int position) {
    }
}
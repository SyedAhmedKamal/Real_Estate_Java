package com.example.realestate_java.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.realestate_java.Adapter.SelectImagesAdapter;
import com.example.realestate_java.R;
import com.example.realestate_java.databinding.ActivityCreateNewAddBinding;
import com.example.realestate_java.model.Post;
import com.example.realestate_java.model.SelectImagesAdapterModel;
import com.example.realestate_java.model.User;
import com.example.realestate_java.repositories.PostRepository;
import com.example.realestate_java.repositories.ProfileInfoRepo;
import com.example.realestate_java.uitl.SelectImagesClickListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;

public class CreateNewAddActivity extends AppCompatActivity implements SelectImagesClickListener, OnMapReadyCallback {

    private static final String TAG = "CreateNewAddActivity";
    private ActivityCreateNewAddBinding binding;
    private ActivityResultLauncher<Intent> activityResultLauncherImages;
    private ArrayList<SelectImagesAdapterModel> imageList;
    private Post postObject;
    private User userInfo;
    protected static String category;
    protected static String subCategory;
    private ArrayList<String> imgUrls;

    private SelectImagesAdapter adapter;
    private ArrayList<SelectImagesAdapterModel> list;

    private GoogleMap mMap;

    private ActivityResultLauncher<Intent> activityResultLauncherMaps;
    private static double karachiLat = 24.8607;
    private static double karachiLang = 67.0011;
    private static String address;
    private static String locality;

    ProfileInfoRepo profileInfoRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNewAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initMapFragment();
        selectImages();
        getLatLangFromMaps();
        setSupportActionBar(binding.crateNewAddToolBar);

        profileInfoRepo = new ProfileInfoRepo();
        profileInfoRepo.getUserInfo().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                userInfo = user;
                Log.d(TAG, "onChanged: called - " + userInfo.getName());
            }
        });


        imgUrls = new ArrayList<>();
        imageList = new ArrayList<>();
        initRecyclerView();


        binding.selectListImageLayout.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            activityResultLauncherImages.launch(intent);
        });

        binding.getLocation.setOnClickListener(view -> {
            Intent intent = new Intent(CreateNewAddActivity.this, MapsActivity.class);
            activityResultLauncherMaps.launch(intent);
        });

        binding.postNowButton.setOnClickListener(view -> {
            uploadPostNow();
        });

        selectChip();

    }

    private void selectChip() {
        binding.houseChip.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                category = "House";
                Log.d(TAG, "uploadPostNow: " + category);
            }
        });

        binding.shopChip.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                category = "Shop";
                Log.d(TAG, "uploadPostNow: " + category);
            }
        });

        binding.plotChip.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                category = "Plot";
                Log.d(TAG, "uploadPostNow: " + category);
            }
        });

        binding.rentChip.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                subCategory = "for rent";
                Log.d(TAG, "uploadPostNow: " + subCategory);
            }
        });

        binding.sellChip.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                subCategory = "for sell";
                Log.d(TAG, "uploadPostNow: " + subCategory);
            }
        });
    }

    private void uploadPostNow() {

        String title = binding.editTextTitle.getText().toString().trim();
        String subTitle = binding.editTextSubtitle.getText().toString().trim();
        String price = binding.editTextPrice.getText().toString().trim();
        String contactInfo = binding.editTextContactInfo.getText().toString().trim();


        Geocoder geocoder = new Geocoder(CreateNewAddActivity.this);
        try {
            // getting address from location (from reverse geocoding)
            ArrayList<Address> addresses = (ArrayList<Address>) geocoder
                    .getFromLocation(karachiLat, karachiLang, 1);
            if (addresses != null) {
                binding.setAddress.setText(addresses.get(0).getAddressLine(0));
                binding.setAddress.setText(addresses.get(0).getLocality());
                address = addresses.get(0).getAddressLine(0); // Full address
                locality = addresses.get(0).getLocality(); // town + district
            }
            Log.i(TAG, "upload now ADDRESS" + addresses.get(0).getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (title.isEmpty()) {
            binding.editTextTitle.setError("Required*");
        } else if (subTitle.isEmpty()) {
            binding.editTextSubtitle.setError("Required*");
        } else if (category == null) {
            Toast.makeText(this, "Category must be selected", Toast.LENGTH_LONG).show();
        } else if (subCategory == null) {
            Toast.makeText(this, "Sub-Category must be selected", Toast.LENGTH_LONG).show();
        } else if (price.isEmpty()) {
            binding.editTextPrice.setError("Required*");
        } else if (contactInfo.isEmpty()) {
            binding.editTextContactInfo.setError("Required*");
        } else if (imageList.size() == 0) {
            Toast.makeText(this, "Select at least one image", Toast.LENGTH_SHORT).show();
        }
        else if (address.isEmpty()){
            Toast.makeText(this, "Select Location", Toast.LENGTH_SHORT).show();
        }
        else {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            PostRepository postRepository = new PostRepository();

            postRepository.createPost(imageList, this).observe(this, new Observer<ArrayList<String>>() {
                @Override
                public void onChanged(ArrayList<String> imgUrlList) {
                    Log.d(TAG, "onChanged: " + imgUrlList.size());
                    postObject = new Post(
                            user.getUid(),
                            userInfo.getName(),
                            userInfo.getProfileImageUrl(),
                            String.valueOf(System.currentTimeMillis()),
                            imgUrlList,
                            title,
                            subTitle,
                            category,
                            subCategory,
                            price,
                            karachiLat,
                            karachiLang,
                            contactInfo,
                            address,
                            locality
                    );

                    postRepository.storeDataToFirebaseDB(postObject).observe(CreateNewAddActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean result) {
                            if (result) {
                                Log.d(TAG, "onChanged: success");
                                finish();
                            } else {
                                Log.d(TAG, "onChanged: failed");
                            }
                        }
                    });
                }
            });

        }
    }


    private void initMapFragment() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.set_map);

        Executors.newSingleThreadExecutor().execute(() -> runOnUiThread(() -> {
            if (mapFragment != null) {
                mapFragment.getMapAsync(CreateNewAddActivity.this);
            }
        }));
    }

    private void initRecyclerView() {
        list = new ArrayList<>();
        if (imageList.size() == 0) {
            binding.selectListImageLayout.setVisibility(View.VISIBLE);
            binding.selectImagesRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            binding.selectListImageLayout.setVisibility(View.INVISIBLE);
            binding.selectImagesRecyclerView.setVisibility(View.VISIBLE);
            adapter = new SelectImagesAdapter(imageList);
            binding.selectImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            binding.selectImagesRecyclerView.setAdapter(adapter);
        }

    }

    private void getLatLangFromMaps() {

        activityResultLauncherMaps = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getData() != null) {
                            karachiLat = result.getData().getDoubleExtra("lat", 24.8607);
                            karachiLang = result.getData().getDoubleExtra("lang", 67.0011);
                            Log.d(TAG, "onActivityResult: newLat" + karachiLat);
                            Log.d(TAG, "onActivityResult: newLang" + karachiLang);

                            Geocoder geocoder = new Geocoder(CreateNewAddActivity.this);
                            try {
                                // getting address from location (from reverse geocoding)
                                ArrayList<Address> addresses = (ArrayList<Address>) geocoder
                                        .getFromLocation(karachiLat, karachiLang, 1);
                                if (addresses != null) {
                                    binding.setAddress.setText(addresses.get(0).getAddressLine(0));
                                }
                                Log.i(TAG, "onMapClick: ADDRESS" + addresses.get(0).getAddressLine(0));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        float zoom = 12f;
        float overlaySize = 50f;

        // Add a marker in Sydney and move the camera
        LatLng karachi = new LatLng(karachiLat, karachiLang);
        mMap.addMarker(new MarkerOptions().position(karachi).title("Marker in Karachi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(karachi));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(karachi, zoom));
    }
}
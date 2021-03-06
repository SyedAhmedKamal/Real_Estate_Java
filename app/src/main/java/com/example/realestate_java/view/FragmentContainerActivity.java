package com.example.realestate_java.view;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.ArraySet;
import android.view.View;

import com.example.realestate_java.R;
import com.example.realestate_java.databinding.ActivityFragmentContainerBinding;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragmentContainerActivity extends AppCompatActivity {

    public ActivityFragmentContainerBinding binding;
    public NavController navController;
    public  NavHostFragment navHostFragment;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFragmentContainerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navConfig();
    }

    private void navConfig() {

        List<Integer> list = Arrays.asList(
                R.id.homeFragment_nav,
                R.id.createNewAddActivity_nav,
                R.id.myFavouritesListFragment_nav,
                R.id.userProfileFragment_nav
        );

        Set<Integer> set = new HashSet<>(list);

        setSupportActionBar(binding.toolbar);

        navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        navController = navHostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(set).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (navDestination.getId() == R.id.viewAddFragment || navDestination.getId() == R.id.viewImagesFragment){
                    binding.bottomNavigationView.setVisibility(View.GONE);
                }
                else {
                    binding.bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}
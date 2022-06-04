package com.example.realestate_java.view.fragments;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.realestate_java.R;
import com.example.realestate_java.network.NetworkStateCheck;

public class NoInternetFragment extends Fragment {

    private NetworkStateCheck networkStateCheck;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_internet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        networkStateCheck = new NetworkStateCheck();
        requireActivity().registerReceiver(networkStateCheck, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        networkStateCheck.check.observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {

                if (result) {
                    requireActivity(). getSupportFragmentManager().popBackStack();
                }
            }
        });
    }
}
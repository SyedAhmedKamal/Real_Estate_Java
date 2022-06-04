package com.example.realestate_java.view.fragments;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestate_java.MainActivity;
import com.example.realestate_java.R;
import com.example.realestate_java.network.NetworkStateCheck;
import com.example.realestate_java.view.FragmentContainerActivity;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private LinearLayout noInternet;
    private TextView textView;
    private Button refreshButton;

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

        textView = view.findViewById(R.id.tv_main);
        noInternet = view.findViewById(R.id.no_internet);
        refreshButton = view.findViewById(R.id.refreshBtn);

        networkStateCheck = new NetworkStateCheck();
        requireActivity().registerReceiver(networkStateCheck, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        networkStateCheck.check.observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                Log.d(TAG, "onChanged: " + result);

                if (result) {
                    textView.setVisibility(View.VISIBLE);
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

        refreshButton.setOnClickListener(view1 -> {
            networkStateCheck.check.observe(requireActivity(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean result) {
                    Log.d(TAG, "onChanged: " + result);

                    if (result) {
                        textView.setVisibility(View.VISIBLE);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().unregisterReceiver(networkStateCheck);
    }
}
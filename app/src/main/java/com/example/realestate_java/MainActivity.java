package com.example.realestate_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.realestate_java.network.NetworkStateCheck;
import com.facebook.shimmer.ShimmerFrameLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private NetworkStateCheck networkStateCheck;
    private TextView textView;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onStart() {
        super.onStart();
        //checkInternet();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        checkInternet();
    }

    private void checkInternet() {
        networkStateCheck = new NetworkStateCheck();
        registerReceiver(networkStateCheck, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        networkStateCheck.check.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                Log.d(TAG, "onChanged: "+result);

                if (result){
                    shimmerFrameLayout.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    textView.setText("Welcome");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkStateCheck);
    }
}
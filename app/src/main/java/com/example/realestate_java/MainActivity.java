package com.example.realestate_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.realestate_java.network.NetworkStateCheck;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private NetworkStateCheck networkStateCheck;
    private TextView textView;

    @Override
    protected void onStart() {
        super.onStart();
        //checkInternet();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkInternet();
    }

    private void checkInternet() {
        networkStateCheck = new NetworkStateCheck();
        registerReceiver(networkStateCheck, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        networkStateCheck.check.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                Log.d(TAG, "onChanged: "+result);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkStateCheck);
    }
}
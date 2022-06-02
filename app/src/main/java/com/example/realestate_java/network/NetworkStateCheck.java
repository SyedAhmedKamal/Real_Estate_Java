package com.example.realestate_java.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkStateCheck extends BroadcastReceiver {

    private static final String TAG = "NetworkStateCheck";
    public MutableLiveData<Boolean> check = new MutableLiveData<>();

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            if (isOnline(context)) {

                ExecutorService executorService = Executors.newSingleThreadExecutor();

                executorService.execute(() -> {

                    Boolean ch = false;

                    // one time check for internet
                    if (internetIsConnected()) {
                        Log.d(TAG, "onReceive: internet is available");
                        check.postValue(true);
                    } else {
                        // return false mean no internet available
                        check.postValue(false);

                        // constantly check for internet
                        while (ch == false) {

                            ch = internetIsConnected();
                            Log.d(TAG, "onReceive: checking....");

                            if (ch==true){
                                // if internet is available then post true and return
                                Log.d(TAG, "onReceive: internet is available");
                                check.postValue(true);
                                return;
                            }

                            try {
                                Thread.sleep(700);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                });

                Log.i(TAG, "onReceive: connected");
            } else {
                check.postValue(false);
                Log.i(TAG, "onReceive: not connected");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            Log.d(TAG, "internetIsConnected: " + e.getLocalizedMessage());
            return false;
        }
    }
}
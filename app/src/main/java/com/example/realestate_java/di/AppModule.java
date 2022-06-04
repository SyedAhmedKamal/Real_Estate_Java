package com.example.realestate_java.di;

import android.content.Context;

import com.example.realestate_java.network.NetworkStateCheck;
import com.example.realestate_java.repositories.SignInRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    public Context provideContext(@ApplicationContext Context context) {
        return context.getApplicationContext();
    }

    @Provides
    @Singleton
    public NetworkStateCheck provideNetworkClass(){
        return new NetworkStateCheck();
    }

    @Provides
    public SignInRepository provideSignInRepo(){
        return new SignInRepository();
    }

}

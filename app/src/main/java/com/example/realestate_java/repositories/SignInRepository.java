package com.example.realestate_java.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.realestate_java.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInRepository {

    FirebaseAuth auth;
    DatabaseReference databaseReference;
    MutableLiveData<User> userMutableLiveData;
    MutableLiveData<Boolean> signInCheck;
    MutableLiveData<Boolean> signUpCheck;
    MutableLiveData<Boolean> authCheck;

    private static final String TAG = "SignInRepository";

    public SignInRepository() {

        this.userMutableLiveData = new MutableLiveData<>();
        this.signInCheck = new MutableLiveData<>();
        this.signUpCheck = new MutableLiveData<>();
        this.authCheck = new MutableLiveData<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();

    }

    public FirebaseUser getUser() {
        return auth.getCurrentUser();
    }

    public MutableLiveData<Boolean> SignInNow(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        signInCheck.postValue(true);
                        Log.d(TAG, "SignInNow: " + task.getResult());
                    } else {
                        Log.d(TAG, "SignInNow: " + task.getException());
                        signInCheck.postValue(false);
                    }

                });
        return signInCheck;
    }

    public MutableLiveData<Boolean> SignUpNow(String email, String password, String name, String phone, String timeStamp) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        User newUser = new User(name, email, password, phone, timeStamp);
                        databaseReference.child(auth.getUid()).setValue(newUser)
                                .addOnCompleteListener(task1 -> {

                                    if (task1.isSuccessful()) {
                                        Log.d(TAG, "SignUpNow: " + task1.getResult());
                                        signUpCheck.postValue(true);
                                    } else {
                                        Log.d(TAG, "SignUpNow: " + task1.getException());
                                        signUpCheck.postValue(false);
                                    }
                                });

                    } else {
                        Log.d(TAG, "SignUpNow: Auth " + task.getException());
                        authCheck.postValue(false);
                    }

                });
        return signUpCheck;
    }

}

package com.example.womansafetyapp;

import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class signout {
    void signout()
    {
        FirebaseAuth.getInstance().signOut();

    }
}

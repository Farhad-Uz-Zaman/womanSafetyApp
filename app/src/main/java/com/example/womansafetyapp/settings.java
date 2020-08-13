package com.example.womansafetyapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */

public class settings extends Fragment implements View.OnClickListener {
    private Button signout;
    View view;

    public settings() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_settings, container, false);
        signout=(Button)view.findViewById(R.id.signout);
        signout.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View v) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getActivity(),"Signed Out",Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(getActivity(), loginActivity.class);
        startActivity(intent);
        //finish();


    }



}

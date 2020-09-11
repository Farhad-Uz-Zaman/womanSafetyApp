package com.example.womansafetyapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class contact extends Fragment {

    EditText contact1, contact2, contact3, phonenum1, phonenum2, phonenum3,email;
    Button save;
    DatabaseReference databaseReference;


    public contact() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        getActivity().setTitle("Contact");


        save = (Button) view.findViewById(R.id.save);
        contact1 = view.findViewById(R.id.contact1);
        contact2 = view.findViewById(R.id.contact2);
        contact3 = view.findViewById(R.id.contact3);
        phonenum1 = view.findViewById(R.id.phonenum1);
        phonenum2 = view.findViewById(R.id.phonenum2);
        phonenum3 = view.findViewById(R.id.phonenum3);
        email = view.findViewById(R.id.email);
        databaseReference = FirebaseDatabase.getInstance().getReference("Contacts");


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String contact1_string = contact1.getText().toString().trim();
                String contact2_string = contact2.getText().toString().trim();
                String contact3_string = contact3.getText().toString().trim();
                String phone1_string = phonenum1.getText().toString().trim();
                String phone2_string = phonenum2.getText().toString().trim();
                String phone3_string = phonenum3.getText().toString().trim();
                String email_string = email.getText().toString().trim();


                String key = databaseReference.push().getKey();
                ContactInfo cn = new ContactInfo(contact1_string, contact2_string,contact3_string,phone1_string,phone2_string,phone3_string,email_string);


                databaseReference.child(key).setValue(cn);

                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();


            }
        });


        return view;

    }



}
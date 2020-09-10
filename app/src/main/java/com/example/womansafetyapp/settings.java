package com.example.womansafetyapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */

public class settings extends Fragment implements View.OnClickListener {

    View view;

    EditText name;
    EditText username;
    EditText phone;
    EditText address;
    Button update;

    String name_string,username_string,phone_string,address_string;
    DatabaseReference reference;

    public settings() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_settings, container, false);



        name=(EditText) view.findViewById(R.id.name);
        username=(EditText)view.findViewById(R.id.username);
        phone=(EditText)view.findViewById(R.id.phone);
        address=(EditText)view.findViewById(R.id.address);
        update=(Button)view.findViewById(R.id.update);

        reference= FirebaseDatabase.getInstance().getReference("Users");


        Bundle bundle= getActivity().getIntent().getExtras();
        if(bundle!=null){
            name_string = bundle.getString("NAME");
            username_string = bundle.getString("USERNAME");
            phone_string = bundle.getString("PHONE");
            address_string = bundle.getString("ADDRESS");

        }

        name.setText(name_string);
        username.setText(username_string);
        phone.setText(phone_string);
        address.setText(address_string);




        return view;
    }
    @Override
    public void onClick(View v) {



        //finish();


    }


public void Update(View v){


        if(isDataChanged()){

            Toast.makeText(getActivity(),"Updated Successfully",Toast.LENGTH_SHORT).show();
            
        }
        else{

            Toast.makeText(getActivity(),"Data is same",Toast.LENGTH_SHORT).show();

        }


}

    private boolean isDataChanged() {

        if(!name_string.equals(name.getText().toString())||!username_string.equals(username.getText().toString())||!phone_string.equals(phone.getText().toString())||!address_string.equals(address.getText().toString())){



            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            reference.child(uid).child("Name").setValue(name.getText().toString());
            reference.child(uid).child("Username").setValue(username.getText().toString());
            reference.child(uid).child("Phone").setValue(phone.getText().toString());
            reference.child(uid).child("Address").setValue(address.getText().toString());

            name_string=name.getText().toString();
            username_string=username.getText().toString();
            phone_string=phone.getText().toString();
            address_string=address.getText().toString();


            return true;


        }

        else{
            return false;
        }


    }


}

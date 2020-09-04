package com.example.womansafetyapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class profile extends Fragment {

    TextView name;
    TextView username;
    TextView mail;
    TextView phone;
    TextView address;
    TextView contact;
    String name_string,username_string,mail_string,phone_string,contact_string,address_string;

    public profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Profile");

        name=(TextView) view.findViewById(R.id.name);
        username=(TextView)view.findViewById(R.id.username);
        mail=(TextView)view.findViewById(R.id.mail);
        phone=(TextView)view.findViewById(R.id.phone);
        //contact=(TextView)view.findViewById(R.id.contact);
        address=(TextView)view.findViewById(R.id.address);


        Bundle bundle= getActivity().getIntent().getExtras();
        if(bundle!=null){
            name_string = bundle.getString("NAME");
            username_string = bundle.getString("USERNAME");
            mail_string = bundle.getString("EMAIL");
            phone_string = bundle.getString("PHONE");
            contact_string=bundle.getString("CONTACT");
            address_string = bundle.getString("ADDRESS");

        }

        name.setText(name_string);
        username.setText(username_string);
        phone.setText(phone_string);
        mail.setText(mail_string);
        address.setText(address_string);

        return view;
    }
}

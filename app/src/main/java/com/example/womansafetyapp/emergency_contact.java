package com.example.womansafetyapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link emergency_contact#newInstance} factory method to
 * create an instance of this fragment.
 */
public class emergency_contact extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText contact1, contact2, contact3, phonenum1, phonenum2, phonenum3,email;
    Button save;
    DatabaseReference databaseReference;

    public emergency_contact() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment emergency_contact.
     */
    // TODO: Rename and change types and number of parameters
    public static emergency_contact newInstance(String param1, String param2) {
        emergency_contact fragment = new emergency_contact();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_emergency_contact, container, false);
        getActivity().setTitle("Emergency Contacts");

        save = (Button) view.findViewById(R.id.save);
        contact1 = view.findViewById(R.id.contact1);
        contact2 = view.findViewById(R.id.contact2);
        contact3 = view.findViewById(R.id.contact3);
        phonenum1 = view.findViewById(R.id.phonenum1);
        phonenum2 = view.findViewById(R.id.phonenum2);
        phonenum3 = view.findViewById(R.id.phonenum3);
        email = view.findViewById(R.id.email);
        databaseReference= FirebaseDatabase.getInstance().getReference("Contacts");


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



                ContactInfo cn = new ContactInfo(contact1_string, contact2_string,contact3_string,phone1_string,phone2_string,phone3_string,email_string);


                FirebaseDatabase.getInstance().getReference("Contacts")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(cn).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();



                                } else {
                                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });





            }
        });


        return view;
    }



}
package com.example.womansafetyapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class dashboard extends Fragment  {

    Button sendReportManually;
    EditText username;
    EditText  details;
    FirebaseDatabase db;
    DatabaseReference dr;


    public dashboard() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        sendReportManually = (Button) view.findViewById(R.id.send);
        username = view.findViewById(R.id.userName);
        details = view.findViewById(R.id.reportDetails);



        sendReportManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = FirebaseDatabase.getInstance();//"ReportManual"
                dr = db.getReference("ReportManual");

                String name = username.getText().toString();
                String detail = details.getText().toString();

                reportManual rm = new reportManual(name,detail);
                dr.child(name).setValue(rm);

            }
        });


        return view;
    }




}

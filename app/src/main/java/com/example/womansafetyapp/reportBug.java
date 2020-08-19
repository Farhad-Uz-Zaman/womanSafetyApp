package com.example.womansafetyapp;

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
 */
public class reportBug extends Fragment {
    Button sendReportManuallys;
    EditText usernames;
    EditText  detailss;
    FirebaseDatabase dbs;
    DatabaseReference drs;

    public reportBug() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report_bug, container, false);

        // Inflate the layout for this fragment

        sendReportManuallys = (Button) v.findViewById(R.id.sends);
        usernames = v.findViewById(R.id.userNames);
        detailss = v.findViewById(R.id.reportDetailss);



        sendReportManuallys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbs = FirebaseDatabase.getInstance();//"ReportManual"
                drs = dbs.getReference("ReportBug");

                String names = usernames.getText().toString();
                String detailsss = detailss.getText().toString();

                if (!names.isEmpty() && !detailsss.isEmpty()){
                    reprtBug rm = new reprtBug(names,detailsss);
                    drs.child(names).setValue(rm);
                    Toast.makeText(getActivity(), "report received", Toast.LENGTH_SHORT).show();
                    usernames.setText("");
                    detailss.setText("");
                }
                else if (names.isEmpty() || detailsss.isEmpty()){
                    Toast.makeText(getActivity(), "fill sections properly", Toast.LENGTH_SHORT).show();
                }


            }
        });
        return v;
    }
}

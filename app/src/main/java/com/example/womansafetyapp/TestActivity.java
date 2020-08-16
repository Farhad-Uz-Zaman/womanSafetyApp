package com.example.womansafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    TextView name;
    TextView username;
    TextView mail;
    TextView phone;
    TextView address;
    String name_string,username_string,mail_string,phone_string,address_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        name=(TextView)findViewById(R.id.name);
        username=(TextView)findViewById(R.id.username);
        mail=(TextView)findViewById(R.id.mail);
        phone=(TextView)findViewById(R.id.phone);
        address=(TextView)findViewById(R.id.address);



        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            name_string = bundle.getString("NAME");
            username_string = bundle.getString("USERNAME");
            mail_string = bundle.getString("EMAIL");
            phone_string = bundle.getString("PHONE");
            address_string = bundle.getString("ADDRESS");

        }

        name.setText(name_string);
        username.setText(username_string);
        phone.setText(phone_string);
        mail.setText(mail_string);
        address.setText(address_string);



    }
}
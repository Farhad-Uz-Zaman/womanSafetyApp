package com.example.womansafetyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;

public class SmsTest extends AppCompatActivity {

String msg,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_test);

        contact="+8801676546443";
        msg="Safety app test";

        ActivityCompat.requestPermissions(SmsTest.this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        SmsManager myManager = SmsManager.getDefault();
        myManager.sendTextMessage(contact,null,msg,null,null);



    }
}
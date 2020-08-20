package com.example.womansafetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SmsTest extends AppCompatActivity {

String msg,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_test);

        contact="+8801993263705";
        msg="Safety app test";

        int checkPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS);


        //ActivityCompat.requestPermissions(SmsTest.this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        //SmsManager myManager = SmsManager.getDefault();
        //myManager.sendTextMessage(contact,null,msg,null,null);

        Bundle bundle = getIntent().getExtras();
        if (bundle!= null){
            if(bundle.getString("some")!=null){
                Toast.makeText(getApplicationContext(),"data"+bundle.getString("some"),Toast.LENGTH_SHORT).show();
                //ActivityCompat.requestPermissions(SmsTest.this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

                //SmsManager myManager = SmsManager.getDefault();
                //myManager.sendTextMessage(contact,null,msg,null,null);
                if(checkPermission==PackageManager.PERMISSION_GRANTED){
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(contact,null,msg,null,null);
                }
                else {Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show();}


            }
        }


    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case 0:
                if(grantResults.length>=0 && grantResults [0]== PackageManager.PERMISSION_GRANTED){
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(contact,null,msg,null,null);
                }
                break;
        }
    }
}
package com.example.womansafetyapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class home extends Fragment implements SensorEventListener {

    private static final String tag = "home";
    TextView xval,yval,zval;
    private SensorManager sensorManager;
    private Sensor accel;
    private boolean accelAvail , isitnotfirsttime = false;

    private float xcurrent,ycurrent,zcurrent;
    private float xlast,ylast,zlast;
    private float xDiff,yDiff,zDiff;
    private float threshold = 9.66f;
    int state=0;
    private Vibrator vibrates;
    private DatabaseReference ref;
    String contact="+8801973376517";
    String msg="Safety app test";
    String email;




    private MediaRecorder record;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private float accellast,accelval,shake;
    private Button emergency;
    View view;

    public home() {
        // Required empty public constructor
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int getpermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (getpermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        emergency=(Button)view.findViewById(R.id.emergencyButton);
        xval = (TextView) view.findViewById(R.id.xValue);
        yval = (TextView) view.findViewById(R.id.yValue);
        zval = (TextView) view.findViewById(R.id.zValue);

        ref= FirebaseDatabase.getInstance().getReference().child("Contacts");


        Bundle bundle= getActivity().getIntent().getExtras();
        if(bundle!=null){

            email = bundle.getString("EMAIL");


        }



        vibrates = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        //sensorManager.registerListener(sensorListener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);  ////////////////////////////////////

        accelval = SensorManager.GRAVITY_EARTH;
        accellast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null){
            accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            accelAvail = true;
        }
        else {
            xval.setText("not found");
            accelAvail = false;
        }





        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,accel, SensorManager.SENSOR_DELAY_NORMAL);

        emergency.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {



                //Intent intent = new Intent(getActivity(),SmsTest.class);
                //intent.putExtra("some","some text");
                //startActivity(intent);






                Intent mediaint=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                mediaint.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1080);
                mediaint.putExtra(MediaStore.EXTRA_DURATION_LIMIT,10);


                startActivityForResult(mediaint,1);
                CameraManager camManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
                String cameraId; // Usually front camera is at 0 position.

                {
                    try {
                        cameraId = camManager.getCameraIdList()[0];
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            camManager.setTorchMode(cameraId, true);
                        }
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                recordAudio();
            }
        });
        return view;
    }
    public void recordAudio()
    {

        emergency.setEnabled(false);
        String filePath= "/storage/emulated/0";
        File audiofiledir= new File(filePath);

        Long date=new Date().getTime();
        Date current_time = new Date(Long.valueOf(date));







        record=new MediaRecorder();
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        verifyStoragePermissions(getActivity());


        record.setAudioSource(MediaRecorder.AudioSource.DEFAULT);

        record.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        record.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        if (!audiofiledir.exists()){
            audiofiledir.mkdirs();
        }

        String audiofilename="/storage/emulated/0"+"/"+current_time+".amr";
        record.setOutputFile(audiofilename);

        try {
            // ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);
            record.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            emergency.setEnabled(true);
            Toast.makeText(getActivity(),"couldn't create audio"+e.getMessage(),Toast.LENGTH_SHORT).show();
            return;
        }
        record.start();
        Toast.makeText(getActivity(),"start"+audiofilename,Toast.LENGTH_SHORT).show();
        CountDownTimer audioduration = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
            }


            public void onFinish() {
                emergency.setEnabled(true);
                Toast.makeText(getActivity(), "30 seconds are up ", Toast.LENGTH_LONG).show();
                record.stop();
                record.reset();
                record.release();
                record=null;


            }};audioduration.start();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==Activity.RESULT_OK && requestCode==1)
        {
            AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

            VideoView videoVIew= new VideoView(getActivity());
            videoVIew.setVideoURI(data.getData());

            videoVIew.start();
            builder.setView(videoVIew).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // MediaPlayer mm = MediaPlayer.create(this, R.raw.buzzer);

        xval.setText("X : " +event.values[0]);
        yval.setText("Y : " +event.values[1]);
        zval.setText("Z : " +event.values[2]);

        xcurrent = event.values[0];
        ycurrent = event.values[1];
        zcurrent = event.values[2];

        if (isitnotfirsttime){
            xDiff = Math.abs(xlast - xcurrent);
            yDiff = Math.abs(ylast - ycurrent);
            zDiff = Math.abs(zlast - zcurrent);

            if ((xDiff > threshold && yDiff > threshold) || (yDiff > threshold && zDiff > threshold) || (xDiff > threshold && zDiff > threshold)){
                //vibrates.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                vibrates.vibrate(1000);





                ref.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                            ContactInfo  contactinfo = dataSnapshot1.getValue(ContactInfo.class);



                              if(email.equals(contactinfo.Mail)) {

                                  SmsManager smsManager1 = SmsManager.getDefault();
                                  smsManager1.sendTextMessage(contactinfo.cont1, null, msg, null, null);
                                  SmsManager smsManager2 = SmsManager.getDefault();
                                  smsManager2.sendTextMessage(contactinfo.cont2, null, msg, null, null);
                                  SmsManager smsManager3 = SmsManager.getDefault();
                                  smsManager3.sendTextMessage(contactinfo.cont3, null, msg, null, null);

                              }



                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });















            }




        }

        xlast = xcurrent;
        ylast = ycurrent;
        zlast = zcurrent;
        isitnotfirsttime = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}

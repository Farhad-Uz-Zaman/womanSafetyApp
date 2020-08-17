package com.example.womansafetyapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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

    private Vibrator vibrates;

    private float accellast,accelval,shake;

    public home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        xval = (TextView) view.findViewById(R.id.xValue);
        yval = (TextView) view.findViewById(R.id.yValue);
        zval = (TextView) view.findViewById(R.id.zValue);

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


        return view;
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
            //    mm.start();
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

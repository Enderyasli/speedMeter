package com.androidnative.speedjava;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.params.MeteringRectangle;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnative.speedjava.business.SlopeService;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunFragment extends Fragment implements SensorEventListener {

    private TextView mSpeed, mAverage,mSlope;
    private SensorManager sensorManager;
    Context context;
    Sensor accelerometer;

    static Fragment newInstance() {
        return new RunFragment();
    }

    public RunFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run, container, false);

        mSpeed = view.findViewById(R.id.counter_speed);
        mSlope = view.findViewById(R.id.tv_slope_run);
        mAverage = view.findViewById(R.id.average);
        context = this.getContext();


        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener((SensorEventListener) RunFragment.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);


        return view;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    void updateUI(String speed, String average) {
        mSpeed.setText(speed);
        mAverage.setText(average);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
/*
        Double angle = SlopeService.getAngleFromSensorEvent(sensorEvent)
        angle = 90 - angle;
        mSlope.setText(SlopeService.formatAngle(angle));

 */

         Double angle = SlopeService.getAngleFromSensorEvent(event);
        mSlope.setText(SlopeService.formatAngle(angle));



    }
}

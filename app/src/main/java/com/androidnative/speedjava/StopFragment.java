package com.androidnative.speedjava;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnative.speedjava.business.SlopeService;

public class StopFragment extends Fragment implements SensorEventListener {

    private static final String ARG_PARAM1 = "param1";

    private short mAverage;
    TextView mSlope;
    private SensorManager sensorManager;
    Context context;
    Sensor accelerometer;

    public StopFragment() {
    }

    static StopFragment newInstance(short param1) {
        StopFragment fragment = new StopFragment();
        Bundle args = new Bundle(1);
        args.putShort(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stop, container, false);

        if (getArguments() != null) {
            mAverage = getArguments().getShort(ARG_PARAM1, (short) 0);
        }

        TextView resultSpeedAverage = view.findViewById(R.id.result_speed);
        TextView subTitle = view.findViewById(R.id.title_stop_result);
        mSlope = view.findViewById(R.id.tv_slope_stop);
        context = this.getContext();

        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener((SensorEventListener) StopFragment.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);


        resultSpeedAverage.setText(String.valueOf(mAverage));

        if (mAverage == 0) {
            subTitle.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Double angle = SlopeService.getAngleFromSensorEvent(event);
        mSlope.setText(SlopeService.formatAngle(angle));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

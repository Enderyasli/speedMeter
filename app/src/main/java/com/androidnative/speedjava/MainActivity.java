package com.androidnative.speedjava;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.IBinder;
import androidx.fragment.app.Fragment;

import com.androidnative.speedjava.business.CAverage;
import com.androidnative.speedjava.business.SlopeService;

public class MainActivity extends BaseActivity implements SensorEventListener, ServiceConnection {

    private static final String RUN_FRAGMENT = "RUN_FRAGMENT";
    private static final String STOP_FRAGMENT = "STOP_FRAGMENT";
    private static final int speedReq = 100;
    SensorEventListener sensorEventListener;

    private CAverage mCalculator;

    @Override
    public int getLayout() {
        return R.layout.activity_main;

    }

    @Override
    public int getFragmentLayout() {
        return R.id.main_activity_fragmentlayout;
    }

    @Override
    public Fragment getFragment() {
        return RunFragment.newInstance();
    }

    @Override
    public String getFragmentTag() {
        return RUN_FRAGMENT;
    }

    @Override
    public void bodyActivity() {
        mCalculator = new CAverage();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, SlopeService.class), this, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(this);
    }




    /**
     * RX JAVA
     */

    @Override
    public void subscriberAction(short speedResult) {


        Fragment fragment = getSupportFragmentManager().findFragmentByTag(RUN_FRAGMENT);

        if (speedResult != 0) {

            if (fragment != null) {
                mCalculator.setValue(speedResult);
                ((RunFragment) fragment).updateUI(String.valueOf(speedResult), mCalculator.getResult());
            } else {
                getSupportFragmentManager().beginTransaction().replace(getFragmentLayout(), getFragment(), getFragmentTag()).commit();
            }

        } else {
            // speedResult = 0
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(getFragmentLayout(), StopFragment.newInstance((short) mCalculator.getAverage()), STOP_FRAGMENT).commit();
            }
        }


    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        sensorEventListener.onSensorChanged(event);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

        sensorEventListener.onAccuracyChanged(sensor, accuracy);

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}

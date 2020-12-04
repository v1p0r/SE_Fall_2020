package com.example.healthmonitor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private String userName = "Default User";
    private String session = "AAAAAA";

    private TextView mTextMessage;
    private HomeFragment homeFragment;
    private StatiticalDataFragment statisticalFragment;
    private SettingsFragment settingsFragment;
    private AdviceFragment adviceFragment;

    private int[] steps;
    private double[] heartrate;
    private int todayStep;
    private double[] todayHeartrate;
    private double nowHeartrate;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    initFragment(0);
                    return true;

                case R.id.navigation_dashboard:
                    initFragment(1);
                    return true;

                case R.id.navigation_advices:
                    initFragment(2);
                    return true;

                case R.id.navigation_settings:
                    initFragment(3);
                    return true;

            }
            return false;
        }
    };

    private void initFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Begin session
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // Hide all Fragment
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance(todayStep, nowHeartrate);
                    transaction.add(R.id.fl_content, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                if (statisticalFragment == null) {
                    statisticalFragment = StatiticalDataFragment.newInstance(steps, todayHeartrate);
                    transaction.add(R.id.fl_content, statisticalFragment);
                } else {
                    transaction.show(statisticalFragment);
                }
                break;
            case 2:
                if (adviceFragment == null) {
                    adviceFragment = AdviceFragment.newInstance(userName, session);
                    transaction.add(R.id.fl_content, adviceFragment);
                } else {
                    transaction.show(adviceFragment);
                }
                break;
            case 3:
                if (settingsFragment == null) {
                    settingsFragment = new SettingsFragment();
                    transaction.add(R.id.fl_content, settingsFragment);
                } else {
                    transaction.show(settingsFragment);
                }
                break;

        }// commit session
        transaction.commit();

    }

    private void hideFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (statisticalFragment != null) {
            transaction.hide(statisticalFragment);
        }
        if (settingsFragment != null) {
            transaction.hide(settingsFragment);
        }
        if (adviceFragment != null) {
            transaction.hide(adviceFragment);
        }

    }

    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private int[] getSteps(){
        int[] steps = {14693, 12088, 8360, 9880, 3201, 231, 18200};
        return steps;
    }

    private double[] getHearrate(int resourceId){
        InputStream inputStream = getResources().openRawResource(resourceId);
        String rawHeartrateData = getString(inputStream);
        String[] strHeartrateData = rawHeartrateData.split("\n");
        double[] fHeartrateData = new double[strHeartrateData.length];
        Log.d("AdviceFragment", "HeartRate.length "+ strHeartrateData.length);
        for (int i=0; i<strHeartrateData.length; i++){
            fHeartrateData[i] = Double.parseDouble(strHeartrateData[i]);
            //Log.d("AdviceFragment", "HeartRate.rawdata\n"+fHeartrateData[i]);
        }
        return fHeartrateData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        steps = getSteps();
        heartrate = getHearrate(R.raw.heartbeat4);
        int todayLength = 24*60;
        todayHeartrate = new double[todayLength/10];
        for (int i=0; i<todayLength/10; i++){
            double totalHeartrate = 0;
            for(int j=0; j<10; j++){
                totalHeartrate += heartrate[i*10+j];
            }
            totalHeartrate /= 10;
            todayHeartrate[i] = totalHeartrate;
        }
        todayStep = steps[steps.length - 1];
        nowHeartrate = heartrate[heartrate.length - 1];
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initFragment(0);
    }

}

package com.example.healthmonitor;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String userName = "Default User";
    private String session = "AAAAAA";

    private TextView mTextMessage;
    private Fragment homeFragment;
    private Fragment statisticalFragment;
    private Fragment settingsFragment;
    private Fragment adviceFragment;

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

                case R.id.navigation_notifications:

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
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_content, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                if (statisticalFragment == null) {
                    statisticalFragment = new StatiticalDataFragment();
                    transaction.add(R.id.fl_content, statisticalFragment);
                } else {
                    transaction.show(statisticalFragment);
                }
                break;
            case 2:
                if (adviceFragment == null){
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initFragment(0);
    }

}

package com.example.healthmonitor;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private int steps;
    private double heartrate;

    private TextView textStep;
    private TextView textHeartrate;

    public static HomeFragment newInstance(int steps, double heartrate) {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        args.putInt("steps", steps);
        args.putDouble("heartrate", heartrate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("HomeFragment", "onViewCreated");
        if (getArguments() != null) {
            steps = getArguments().getInt("steps");
            Log.d("HomeFragment", "steps: " + steps);
            heartrate = getArguments().getDouble("heartrate");
            Log.d("HomeFragment", "heartrate: " + heartrate);
        }
        else {
            steps = -1;
            heartrate = -1;
        }
        textStep = view.findViewById(R.id.steps_num);
        textHeartrate = view.findViewById(R.id.text_heartrate);
        Handler handler_steps = new Handler();
        handler_steps.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (steps >= 0)
                    textStep.setText(Integer.toString((int)steps));
                else
                    textStep.setText("error.");
            }
        }, 3000);
        Handler handler_heart = new Handler();
        handler_heart.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (heartrate >= 0)
                    textHeartrate.setText(Integer.toString((int)heartrate));
                else
                    textHeartrate.setText("error");
            }
        }, 3500);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("HomeFragment", "onCreateview");
        return inflater.inflate(R.layout.home, container, false);
        
    }
}

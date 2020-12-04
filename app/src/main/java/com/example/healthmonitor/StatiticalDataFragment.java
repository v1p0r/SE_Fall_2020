package com.example.healthmonitor;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class StatiticalDataFragment extends Fragment {
    private int[] steps;
    private double[] heartrate;


    public static StatiticalDataFragment newInstance(int[] steps, double[] heartrate) {
        Bundle args = new Bundle();
        args.putIntArray("steps", steps);
        args.putDoubleArray("heartrate", heartrate);
        StatiticalDataFragment fragment = new StatiticalDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.statistical_data_page, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.statistical_tool_bar, menu);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            steps = getArguments().getIntArray("steps");
            // Log.d("HomeFragment", "steps: " + steps);
            heartrate = getArguments().getDoubleArray("heartrate");
            // Log.d("HomeFragment", "heartrate: " + heartrate);
        }
        else {
            steps = new int[]{-1};
            heartrate = new double[]{-1};
        }

        Spinner typeSpinner = view.findViewById(R.id.spinner_summary);

        LineChart mLineChartHeartrate = view.findViewById(R.id.lineChart);

        //显示边界
        mLineChartHeartrate.setDrawBorders(true);
        BarChart mBarChartSteps = view.findViewById(R.id.barChart);

        List<BarEntry> listSteps = new ArrayList<>();
        //设置数据
        List<Entry> listHeartrate = new ArrayList<>();

        for (int i=0; i<heartrate.length; i++){
            listHeartrate.add(new Entry(i, (float) heartrate[i]));
        }
        for (int i=0; i<steps.length; i++){
            listSteps.add(new BarEntry(i, steps[i]));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(listHeartrate, "HeartBeat");
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setLineWidth(3f);
        LineData lineData = new LineData(lineDataSet);
        lineData.setDrawValues(false);
        mLineChartHeartrate.setData(lineData);
        mLineChartHeartrate.getDescription().setEnabled(false);
        mLineChartHeartrate.getAxisRight().setEnabled(false);
        mLineChartHeartrate.setDrawBorders(false);

        BarDataSet barDataSet = new BarDataSet(listSteps, "Steps");
        barDataSet.setColor(Color.BLUE);
        BarData barData = new BarData(barDataSet);
        mBarChartSteps.setData(barData);

        barData.setBarWidth(0.3f);//柱子的宽度


        mBarChartSteps.getXAxis().setCenterAxisLabels(true);
        //.getXAxis().setAxisMaximum(7);   //X轴最大数值
        //mBarChartSteps.getXAxis().setAxisMinimum(1);   //X轴最小数值
        mBarChartSteps.getAxisRight().setEnabled(false);//右侧Y轴不显示   默认为显示
        mBarChartSteps.getDescription().setEnabled(false);


    }
}

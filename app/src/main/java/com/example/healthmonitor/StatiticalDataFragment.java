package com.example.healthmonitor;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.statistical_data_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LineChart mLineChart = view.findViewById(R.id.lineChart);
        //显示边界
        mLineChart.setDrawBorders(true);
        BarChart mBarChart = view.findViewById(R.id.barChart);

        List<BarEntry> blist = new ArrayList<>();
        //设置数据
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            entries.add(new Entry(i, (float) (Math.random()) * 100));
            blist.add(new BarEntry(i, (float) (Math.random()) * 8000));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "HeartBeat");
        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);
        mLineChart.getDescription().setEnabled(false);


        BarDataSet barDataSet = new BarDataSet(blist, "Steps");
        barDataSet.setColor(Color.BLUE);
        BarData barData = new BarData(barDataSet);
        mBarChart.setData(barData);

        barData.setBarWidth(0.3f);//柱子的宽度


        mBarChart.getXAxis().setCenterAxisLabels(true);
        mBarChart.getXAxis().setAxisMaximum(7);   //X轴最大数值
        mBarChart.getXAxis().setAxisMinimum(1);   //X轴最小数值
        mBarChart.getAxisRight().setEnabled(false);//右侧Y轴不显示   默认为显示
        mBarChart.getDescription().setEnabled(false);

    }
}

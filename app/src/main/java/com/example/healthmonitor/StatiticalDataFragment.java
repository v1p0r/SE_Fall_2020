package com.example.healthmonitor;

import android.graphics.Color;
import android.os.Bundle;
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
        Spinner typeSpinner = view.findViewById(R.id.spinner_summary);

        LineChart mLineChart = view.findViewById(R.id.lineChart);

        LineChart hLineChart = view.findViewById(R.id.lineChart_temp);
        //显示边界
        mLineChart.setDrawBorders(true);
        BarChart mBarChart = view.findViewById(R.id.barChart);

        List<BarEntry> blist = new ArrayList<>();
        //设置数据
        List<Entry> heartbeatList = new ArrayList<>();

        List<Entry> tempList = new ArrayList<>();


        for (int i = 1; i < 8; i++) {
            heartbeatList.add(new Entry(i, (float) (Math.random()) * 100));
            tempList.add(new Entry(i, (float) (Math.random()) * 30));
            blist.add(new BarEntry(i, (float) (Math.random()) * 8000));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(heartbeatList, "HeartBeat");
        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);
        mLineChart.getDescription().setEnabled(false);

        LineDataSet tempDataSet = new LineDataSet(tempList, "Temp");
        LineData tempdata = new LineData(tempDataSet);
        hLineChart.setData(tempdata);
        hLineChart.getDescription().setEnabled(false);


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

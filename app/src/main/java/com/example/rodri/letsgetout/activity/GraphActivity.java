package com.example.rodri.letsgetout.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rodri.letsgetout.database.MyDataSource;
import com.example.rodri.letsgetout.model.GenericBudget;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 8/1/2016.
 */
public class GraphActivity extends AppCompatActivity {

    private MyDataSource dataSource;
    private List<GenericBudget> genericBudgets;

    private BarDataSet dataSet;
    private ArrayList<BarEntry> entries;
    private ArrayList<String> labels;
    private BarChart barChart;
    private BarData barData;
    private ArrayList<IBarDataSet> dataSets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSource = new MyDataSource(this);
        try {
            dataSource.open();
            genericBudgets = dataSource.getAllExpensesAndSavings();
            createEntriesAndLabels();

            dataSet = new BarDataSet(entries, "#test");
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            barChart = new BarChart(getApplicationContext());
            setContentView(barChart);

            // just trying
            dataSets = new ArrayList<>();
            dataSets.add(dataSet);

            barData = new BarData(dataSets);
            barChart.setData(barData);
            barChart.setDescription("# just a random description");
            barChart.invalidate();

        } catch (Exception e) {
            dataSource.close();
            e.printStackTrace();
        }
    }

    public void createEntriesAndLabels() {
        entries = new ArrayList<>();
        entries.add(new BarEntry(0, 150));
        entries.add(new BarEntry(1, 263));
        entries.add(new BarEntry(2, 547));
        entries.add(new BarEntry(3, 958));
        entries.add(new BarEntry(4, 457));

        labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
    }
}

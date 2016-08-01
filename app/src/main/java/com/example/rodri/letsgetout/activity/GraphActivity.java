package com.example.rodri.letsgetout.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rodri.letsgetout.database.MyDataSource;
import com.example.rodri.letsgetout.model.GenericBudget;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

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
            barChart = new BarChart(getApplicationContext());
            setContentView(barChart);

            // just trying
            dataSets = new ArrayList<>();
            dataSets.add(dataSet);

            barData = new BarData(dataSets);
            barChart.setDescription("# just a random description");

        } catch (Exception e) {
            dataSource.close();
            e.printStackTrace();
        }
    }

    public void createEntriesAndLabels() {
        entries = new ArrayList<>();
        entries.add(new BarEntry(150, 0));
        entries.add(new BarEntry(263, 1));
        entries.add(new BarEntry(547, 2));
        entries.add(new BarEntry(958, 3));
        entries.add(new BarEntry(457, 4));

        labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
    }
}

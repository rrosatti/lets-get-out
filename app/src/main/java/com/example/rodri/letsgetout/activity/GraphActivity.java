package com.example.rodri.letsgetout.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.database.MyDataSource;
import com.example.rodri.letsgetout.model.MonthlyBalance;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 8/2/2016.
 */
public class GraphActivity extends AppCompatActivity {

    private PieChart pieChart;

    private MyDataSource dataSource;
    private List<MonthlyBalance> monthlyBalances;

    private ArrayList<Entry> entries = new ArrayList<>();
    private PieDataSet dataSet;
    private ArrayList<String> labels = new ArrayList<>();
    private PieData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        pieChart = (PieChart) findViewById(R.id.pieChart);

        dataSource = new MyDataSource(this);
        try {
            dataSource.open();
            monthlyBalances = dataSource.getAllMonthlyBalances();
            for (MonthlyBalance mb: monthlyBalances) {
                Toast.makeText(getApplicationContext(), "Month: " + mb.getMonth() +
                        " year: " + mb.getYear() + " totalExpenses: " + mb.getTotalExpenses() +
                        " totalSavings: " + mb.getTotalSavings() + " balance: " + mb.getBalance(), Toast.LENGTH_LONG).show();
            }
            dataSource.close();
        } catch (Exception e) {
            dataSource.close();
            e.printStackTrace();
        }

        setEntries();
        dataSet = new PieDataSet(entries, "#testing");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        setLabels();
        data = new PieData(labels, dataSet);
        pieChart.setData(data);
        pieChart.setDescription("Description");
        pieChart.animateY(2000);
    }

    public void setEntries() {
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(12f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));
    }

    public void setLabels() {
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
    }
}

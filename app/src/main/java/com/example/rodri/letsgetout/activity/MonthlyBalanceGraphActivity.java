package com.example.rodri.letsgetout.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.database.MyDataSource;
import com.example.rodri.letsgetout.model.MonthlyBalance;
import com.example.rodri.letsgetout.util.CustomValueFormatter;
import com.example.rodri.letsgetout.util.Util;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

/**
 * Created by rodri on 8/4/2016.
 */
public class MonthlyBalanceGraphActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtGraphLabel;
    private PieChart pieChart;

    private long monthlyBalanceId = -1;

    private MyDataSource dataSource;
    private MonthlyBalance mb;
    private String[] months;

    // variables related to PieChart
    private PieData pieData;
    private PieDataSet pieDataSet;
    private ArrayList<Entry> entries = new ArrayList<>();
    private ArrayList<String> labels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_balance_graph);

        txtGraphLabel = (TextView) findViewById(R.id.txtGraphLabel);
        pieChart = (PieChart) findViewById(R.id.pieChart);
        toolbar = (Toolbar) findViewById(R.id.toolbarMonthlyBalanceGraph);

        months = getResources().getStringArray(R.array.months);
        dataSource = new MyDataSource(this);
        int[] pieChartColors = {getApplicationContext().getResources().getColor(R.color.pie_chart_expenses),
                            getApplicationContext().getResources().getColor(R.color.pie_chart_savings)};

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            monthlyBalanceId = extras.getLong("monthly_balance_id");
        } else {
            Toast.makeText(this, R.string.toast_invalid_id, Toast.LENGTH_SHORT).show();
            dataSource.close();
            finish();
        }

        toolbar.setTitle(R.string.toolbar_monthly_balance_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        try {
            dataSource.open();

            mb = dataSource.getMonthlyBalance(monthlyBalanceId);

            txtGraphLabel.setText(months[mb.getMonth() - 1]+"/"+mb.getYear());

            entries.add(new Entry(mb.getTotalExpenses(), 0));
            entries.add(new Entry(mb.getTotalSavings(), 1));

            labels.add(String.valueOf(R.string.label_expenses));
            labels.add(String.valueOf(R.string.label_savings));

            pieDataSet = new PieDataSet(entries, null);
            pieDataSet.setColors(pieChartColors);
            pieDataSet.setValueFormatter(new CustomValueFormatter());
            pieData = new PieData(labels, pieDataSet);
            pieData.setValueTextColor(getApplicationContext().getResources().getColor(R.color.pie_chart_description));
            pieChart.setData(pieData);
            pieChart.setDescription(months[mb.getMonth() - 1]+"/"+mb.getYear());
            pieChart.animateY(1200);

            dataSource.close();

        } catch (Exception e) {
            dataSource.close();
            e.printStackTrace();
        }

    }
}

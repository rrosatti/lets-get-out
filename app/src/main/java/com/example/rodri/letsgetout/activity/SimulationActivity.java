package com.example.rodri.letsgetout.activity;

import android.app.DatePickerDialog;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.database.MyDataSource;
import com.example.rodri.letsgetout.model.CurrentBalance;
import com.example.rodri.letsgetout.util.Util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by rodri on 7/29/2016.
 */
public class SimulationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btGetMySettings;
    private EditText etEstimatedValue;
    private Button btSetTargetDate;
    private Button btSimulate;
    private TextView txtMonthlySavingsExpectedLabel;
    private TextView txtMonthlySavingsExpected;
    private TextView txtMonthsExpectedLabel;
    private TextView txtMonthsExpected;
    private TextView txtResults;
    private TextView txtTargetDateLabel;
    private TextView txtTargetDate;

    private MyDataSource dataSource;
    private CurrentBalance currentBalance;

    private int day = 0, motnh = 0, year = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        toolbar = (Toolbar) findViewById(R.id.toolbarSimulation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();
        setStyle(); // set font type for all the TextViews, Buttons and EditTexts

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btGetMySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dataSource = new MyDataSource(getApplicationContext());
                    dataSource.open();

                    if (dataSource.isThereAnyCurrentBalance()) {
                        currentBalance = dataSource.getCurrentBalance(1);

                        etEstimatedValue.setText(Util.setNumberFormat(currentBalance.getEstimatedValue()));
                        String date = currentBalance.getDay() + "/" + currentBalance.getMonth() + "/" + currentBalance.getYear();
                        txtTargetDate.setText(date);
                        txtTargetDateLabel.setVisibility(View.VISIBLE);
                    }


                } catch (SQLException e) {
                    dataSource.close();
                    e.printStackTrace();
                }
            }
        });

        btSetTargetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int yeaR, int monthOfYear, int dayOfMonth) {
                        day = dayOfMonth;
                        motnh = monthOfYear + 1;
                        year = yeaR;
                        String date = day+"/"+motnh+"/"+year;
                        txtTargetDate.setText(date);
                        txtTargetDateLabel.setVisibility(View.VISIBLE);
                    }
                };

                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog datePickerDialog = new DatePickerDialog(SimulationActivity.this, R.style.AppTheme,
                        dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

    }

    public void initializeViews() {
        etEstimatedValue = (EditText) findViewById(R.id.simulation_etEstimatedValue);

        btGetMySettings = (Button) findViewById(R.id.btGetMySettings);
        btSetTargetDate = (Button) findViewById(R.id.simulation_btSetTargetDate);
        btSimulate = (Button) findViewById(R.id.simulation_btSimulate);

        txtMonthlySavingsExpectedLabel = (TextView) findViewById(R.id.simulation_txtMonthlySavingsExpectedLabel);
        txtMonthlySavingsExpected = (TextView) findViewById(R.id.simulation_txtMonthlySavingsExpected);
        txtMonthsExpectedLabel = (TextView) findViewById(R.id.simulation_txtMonthsExpectedLabel);
        txtMonthsExpected = (TextView) findViewById(R.id.simulation_txtMonthsExpected);
        txtResults = (TextView) findViewById(R.id.simulation_txtResults);
        txtTargetDateLabel = (TextView) findViewById(R.id.simulation_txtTargetDateLabel);
        txtTargetDate = (TextView) findViewById(R.id.simulation_txtTargetDate);

        txtTargetDateLabel.setVisibility(View.GONE);
    }

    public void setStyle() {
        Util.setTypeFace(getApplicationContext(), etEstimatedValue, "Quicksand-Italic.otf");

        Util.setTypeFace(getApplicationContext(), btGetMySettings, "Quicksand.otf");
        Util.setTypeFace(getApplicationContext(), btSetTargetDate, "Quicksand.otf");
        Util.setTypeFace(getApplicationContext(), btSimulate, "Quicksand.otf");

        Util.setTypeFace(getApplicationContext(), txtMonthlySavingsExpectedLabel, "Quicksand-Bold.otf");
        Util.setTypeFace(getApplicationContext(), txtMonthlySavingsExpected, "Quicksand.otf");
        Util.setTypeFace(getApplicationContext(), txtMonthsExpectedLabel, "Quicksand-Bold.otf");
        Util.setTypeFace(getApplicationContext(), txtMonthsExpected, "Quicksand.otf");
        Util.setTypeFace(getApplicationContext(), txtResults, "Quicksand-Bold.otf");
        Util.setTypeFace(getApplicationContext(), txtTargetDateLabel, "Quicksand-Bold.otf");
        Util.setTypeFace(getApplicationContext(), txtTargetDate, "Quicksand.otf");
    }
}

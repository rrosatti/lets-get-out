package com.example.rodri.letsgetout.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.util.Util;

/**
 * Created by rodri on 7/29/2016.
 */
public class SimulationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btGetMySettings;
    private EditText etEstimatedValue;
    private EditText etTargetDate;
    private Button btSetTargetDate;
    private Button btSimulate;
    private TextView txtMonthlySavingsExpectedLabel;
    private TextView txtMonthlySavingsExpected;
    private TextView txtMonthsExpectedLabel;
    private TextView txtMonthsExpected;

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
    }

    public void initializeViews() {
        etEstimatedValue = (EditText) findViewById(R.id.simulation_etEstimatedValue);
        etTargetDate = (EditText) findViewById(R.id.simulation_etTargetDate);

        btGetMySettings = (Button) findViewById(R.id.btGetMySettings);
        btSetTargetDate = (Button) findViewById(R.id.simulation_btSetTargetDate);
        btSimulate = (Button) findViewById(R.id.simulation_btSimulate);

        txtMonthlySavingsExpectedLabel = (TextView) findViewById(R.id.simulation_txtMonthlySavingsExpectedLabel);
        txtMonthlySavingsExpected = (TextView) findViewById(R.id.simulation_txtMonthlySavingsExpected);
        txtMonthsExpectedLabel = (TextView) findViewById(R.id.simulation_txtMonthsExpectedLabel);
        txtMonthsExpected = (TextView) findViewById(R.id.simulation_txtMonthsExpected);
    }

    public void setStyle() {
        Util.setTypeFace(getApplicationContext(), etEstimatedValue, "Quicksand-Italic.otf");
        Util.setTypeFace(getApplicationContext(), etTargetDate, "Quicksand-Italic.otf");

        Util.setTypeFace(getApplicationContext(), btGetMySettings, "Quicksand.otf");
        Util.setTypeFace(getApplicationContext(), btSetTargetDate, "Quicksand.otf");
        Util.setTypeFace(getApplicationContext(), btSimulate, "Quicksand.otf");

        Util.setTypeFace(getApplicationContext(), txtMonthlySavingsExpectedLabel, "Quicksand-Bold.otf");
        Util.setTypeFace(getApplicationContext(), txtMonthlySavingsExpected, "Quicksand.otf");
        Util.setTypeFace(getApplicationContext(), txtMonthsExpectedLabel, "Quicksand-Bold.otf");
        Util.setTypeFace(getApplicationContext(), txtMonthsExpected, "Quicksand.otf");
    }
}

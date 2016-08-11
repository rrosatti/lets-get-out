package com.example.rodri.letsgetout.activity;

import android.app.DatePickerDialog;
import android.database.SQLException;
import android.os.Bundle;
import android.os.PersistableBundle;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by rodri on 7/29/2016.
 */
public class SimulationActivity extends AppCompatActivity {

    private static final String STATE_ESTIMATED_VALUE = "estimated_value";
    private static final String STATE_DAY = "day";
    private static final String STATE_MONTH = "month";
    private static final String STATE_YEAR = "year";
    private static final String STATE_IS_BT_SIMULATE_CLICKED = "is_bt_simulate_clicked";

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

    private int day = 0, month = 0, year = 0;
    private boolean isBtSimulateClicked = false; // it will be used to show results when onSaveInstanceState

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        toolbar = (Toolbar) findViewById(R.id.toolbarSimulation);
        toolbar.setTitle(R.string.toolbar_simulation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize Views and set custom font to them
        initializeViews();
        setStyle();

        // Get InstanceState if the screen changes from portrait to landscape (vice versa)
        if (savedInstanceState != null) {
            try {
                etEstimatedValue.setText(savedInstanceState.getString(STATE_ESTIMATED_VALUE));
                day = savedInstanceState.getInt(STATE_DAY);
                month = savedInstanceState.getInt(STATE_MONTH);
                year = savedInstanceState.getInt(STATE_YEAR);

                if (day != 0) {
                    txtTargetDate.setText(day + "/" + month + "/" + year);
                    txtTargetDateLabel.setVisibility(View.VISIBLE);
                }

                isBtSimulateClicked = savedInstanceState.getBoolean(STATE_IS_BT_SIMULATE_CLICKED);
                // Check whether the user has clicked in the Simulate button or not before changing screen orientation
                if (isBtSimulateClicked) {
                    simulate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Get the user's settings related to the Current Balance
        btGetMySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dataSource = new MyDataSource(getApplicationContext());
                    dataSource.open();

                    if (dataSource.isThereAnyCurrentBalance()) {
                        currentBalance = dataSource.getCurrentBalance(1);

                        etEstimatedValue.setText(String.valueOf(currentBalance.getEstimatedValue()
                                - currentBalance.getAchievedValue()));
                        day = currentBalance.getDay();
                        month = currentBalance.getMonth();
                        year = currentBalance.getYear();
                        String date = day+"/"+month+"/"+year;
                        txtTargetDate.setText(date);
                        txtTargetDateLabel.setVisibility(View.VISIBLE);
                    }


                } catch (SQLException e) {
                    dataSource.close();
                    e.printStackTrace();
                }
            }
        });

        // Create a "Calendar Dialog" in order to get the values for day, month and year
        btSetTargetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int yeaR, int monthOfYear, int dayOfMonth) {
                        day = dayOfMonth;
                        month = monthOfYear + 1;
                        year = yeaR;
                        String date = day+"/"+ month +"/"+year;
                        txtTargetDate.setText(date);
                        txtTargetDateLabel.setVisibility(View.VISIBLE);
                    }
                };


                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                // Check if the user got his settings or not (btGetMySettings)
                // If yes, than we just set the Calendar with the given date
                if (day != 0) {
                    cal.set(year, month - 1, day);
                }


                DatePickerDialog datePickerDialog = new DatePickerDialog(SimulationActivity.this, R.style.AppTheme,
                        dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        btSimulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if any field was left empty
                if (etEstimatedValue.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.toast_estimated_value_field_empty, Toast.LENGTH_SHORT).show();
                } else if (day == 0) {
                    Toast.makeText(getApplicationContext(), R.string.toast_set_a_date, Toast.LENGTH_SHORT).show();
                } else {
                    simulate();
                }

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
        txtResults.setVisibility(View.GONE);
        txtMonthlySavingsExpectedLabel.setVisibility(View.GONE);
        txtMonthsExpectedLabel.setVisibility(View.GONE);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(STATE_ESTIMATED_VALUE, etEstimatedValue.getText().toString());
        outState.putBoolean(STATE_IS_BT_SIMULATE_CLICKED, isBtSimulateClicked);
        if (day != 0) {
            outState.putInt(STATE_DAY, day);
            outState.putInt(STATE_MONTH, month);
            outState.putInt(STATE_YEAR, year);
        } else {
            outState.putInt(STATE_DAY, 0);
            outState.putInt(STATE_MONTH, 0);
            outState.putInt(STATE_YEAR, 0);
        }
    }

    public void simulate() {
        txtResults.setVisibility(View.VISIBLE);
        // calculate how many months are still remaining till the target date
        // target year - current year
        int years = year - Calendar.getInstance().get(Calendar.YEAR);
        // target month - current month
        int months = month - Calendar.getInstance().get(Calendar.MONTH);
        int monthsRemaining = (years * 12) + months;
        txtMonthsExpected.setText(String.valueOf(monthsRemaining));
        txtMonthsExpectedLabel.setVisibility(View.VISIBLE);


        // calculate the monthly savings till the target date
        // estimated value / monthsRemaining
        String estimatedValueString = etEstimatedValue.getText().toString();
        float estimatedValue = Float.valueOf(estimatedValueString.replace(",", ""));
        float monthlySavingsExpected = estimatedValue/monthsRemaining;
        txtMonthlySavingsExpected.setText("R$ " + Util.setNumberFormat(monthlySavingsExpected));
        txtMonthlySavingsExpectedLabel.setVisibility(View.VISIBLE);

        isBtSimulateClicked = true;
    }
}

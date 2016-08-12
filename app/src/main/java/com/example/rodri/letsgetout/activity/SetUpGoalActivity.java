package com.example.rodri.letsgetout.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.database.MyDataSource;
import com.example.rodri.letsgetout.model.CurrentBalance;
import com.example.rodri.letsgetout.util.Util;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by rodri on 7/16/2016.
 */
public class SetUpGoalActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btSetTargetDate;
    private Button btConfirm;
    private EditText etEstimatedValue;

    private int day = 0, month = 0, year = 0;

    private MyDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_goal);

        // Initialize Views
        toolbar = (Toolbar) findViewById(R.id.toolbarSetUpGoal);
        btSetTargetDate = (Button) findViewById(R.id.btSetTargetDate);
        btConfirm = (Button) findViewById(R.id.setupgoal_btConfirm);
        etEstimatedValue = (EditText) findViewById(R.id.etEstimatedValue);

        // Set Custom font style to the Views
        Util.setTypeFace(getApplicationContext(), btConfirm, "Quicksand.otf");
        Util.setTypeFace(getApplicationContext(), btSetTargetDate, "Quicksand.otf");
        Util.setTypeFace(getApplicationContext(), etEstimatedValue, "Quicksand-Italic.otf");

        dataSource = new MyDataSource(getApplicationContext());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
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
                        Toast.makeText(getApplicationContext(), day+"/"+month+"/"+year, Toast.LENGTH_SHORT).show();
                    }
                };

                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog datePickerDialog = new DatePickerDialog(SetUpGoalActivity.this, R.style.AppTheme, dateSetListener,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();

            }
        });


        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dataSource.open();

                    String estimatedValue = etEstimatedValue.getText().toString();
                    // check if any field was left empty
                    if (estimatedValue.equals("")) {
                        Toast.makeText(getApplicationContext(), R.string.toast_estimated_value_field_empty, Toast.LENGTH_SHORT).show();
                        return;
                    } else if (day ==  0) {
                        Toast.makeText(getApplicationContext(), R.string.toast_set_a_date, Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        dataSource.createCurrentBalance(Double.valueOf(estimatedValue), 0.00, day, month, year);
                        Toast.makeText(getApplicationContext(), R.string.toast_new_goal_created, Toast.LENGTH_SHORT).show();

                        setResult(Activity.RESULT_OK);
                        finish();
                    }

                } catch (Exception e) {
                    dataSource.close();
                    e.printStackTrace();
                }

                dataSource.close();
            }
        });


    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}

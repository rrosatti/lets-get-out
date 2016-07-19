package com.example.rodri.letsgetout.activity;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rodri on 7/19/2016.
 */
public class UpdateGoalActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etUpdateEstimatedValue;
    private Button btUpdateTargetDate;
    private Button btConfirm;

    private int day, month, year;

    private MyDataSource dataSource;
    private CurrentBalance currentBalance;

    /**
     * This activity uses the activity_setup_goal
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_goal);

        toolbar = (Toolbar) findViewById(R.id.toolbarSetUpGoal);
        etUpdateEstimatedValue = (EditText) findViewById(R.id.etEstimatedValue);
        btUpdateTargetDate = (Button) findViewById(R.id.btSetTargetDate);
        btConfirm = (Button) findViewById(R.id.setupgoal_btConfirm);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dataSource = new MyDataSource(this);

        try {
            dataSource.open();

            currentBalance = dataSource.getCurrentBalance(1);

            // Get the target date: day, month and year
            day = currentBalance.getDay();
            month = currentBalance.getMonth();
            year = currentBalance.getYear();


            etUpdateEstimatedValue.setText(String.valueOf(currentBalance.getEstimatedValue()));
            btUpdateTargetDate.setOnClickListener(new View.OnClickListener() {
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

                    String dateString = day+"-"+month+"-"+year;

                    SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
                    Date date = null;
                    try {
                        date = format.parse(dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateGoalActivity.this, R.style.AppTheme, dateSetListener,
                            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                    datePickerDialog.show();
                }
            });

            btConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String estimatedValue = etUpdateEstimatedValue.getText().toString();
                    if (estimatedValue.equals("")) {
                        Toast.makeText(UpdateGoalActivity.this, "Estimated value cannot be blank!", Toast.LENGTH_SHORT).show();
                    } else {

                        dataSource.updateCurrentBalance(1, Float.valueOf(estimatedValue),
                                currentBalance.getAchievedValue(), day, month, year);
                        Toast.makeText(UpdateGoalActivity.this, "Current Goal was successfully updated!", Toast.LENGTH_SHORT).show();
                        onBackPressed();

                        // Try to find a way to update the current balance values(fragment)
                        // when coming back to CurrentBalanceFragment
                    }

                }
            });


        } catch (Exception e) {
            dataSource.close();
            e.printStackTrace();
        }


    }
}

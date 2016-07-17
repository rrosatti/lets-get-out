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
import com.example.rodri.letsgetout.util.Util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by rodri on 7/17/2016.
 */
public class NewExpenseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etName;
    private EditText etValue;
    private Button btSetDate;
    private Button btConfifm;
    private int day, month, year;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);

        toolbar = (Toolbar) findViewById(R.id.toolbarNewExpense);
        etName = (EditText) findViewById(R.id.etExpenseName);
        etValue = (EditText) findViewById(R.id.etExpenseValue);
        btSetDate = (Button) findViewById(R.id.newExpense_btSetDate);
        btConfifm = (Button) findViewById(R.id.newExpense_btConfirm);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btSetDate.setOnClickListener(new View.OnClickListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewExpenseActivity.this, R.style.AppTheme, dateSetListener,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        // Need to save data into database (get data from EditTexts, implement onClick() event for btConfirm, use MyDataSource)

    }
}

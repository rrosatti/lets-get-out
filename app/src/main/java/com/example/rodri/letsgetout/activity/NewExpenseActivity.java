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
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.database.MyDataSource;
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
    private Button btConfirm;
    private TextView txtNewExpenseTitle;

    private int day = 0, month = 0, year = 0;

    private MyDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);

        toolbar = (Toolbar) findViewById(R.id.toolbarNewExpense);
        etName = (EditText) findViewById(R.id.etExpenseName);
        etValue = (EditText) findViewById(R.id.etExpenseValue);
        btSetDate = (Button) findViewById(R.id.newExpense_btSetDate);
        btConfirm = (Button) findViewById(R.id.newExpense_btConfirm);
        txtNewExpenseTitle = (TextView) findViewById(R.id.txtNewExpense);

        // Apply custom font type to the EditTexts, Buttons and TextViews
        setStyle();

        dataSource = new MyDataSource(this);

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
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etName.getText().toString();
                String value = etValue.getText().toString();

                if (description.equals("")) {
                    Toast.makeText(getApplicationContext(), "The description field cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (value.equals("")) {
                    Toast.makeText(getApplicationContext(), "The value field cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (day == 0) {
                    Toast.makeText(getApplicationContext(), "You need to set a date!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    try {
                        dataSource.open();

                        dataSource.createExpense(description, Float.valueOf(value), day, month, year);
                        dataSource.close();
                        Toast.makeText(getApplicationContext(), "A new expense was successfully created!", Toast.LENGTH_SHORT).show();
                        onBackPressed();

                    } catch (Exception e) {
                        dataSource.close();
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void setStyle() {
        Util.setTypeFace(getApplicationContext(), etName, "Quicksand-Italic.otf");
        Util.setTypeFace(getApplicationContext(), etValue, "Quicksand-Italic.otf");

        Util.setTypeFace(getApplicationContext(), btConfirm, "Quicksand.otf");
        Util.setTypeFace(getApplicationContext(), btSetDate, "Quicksand.otf");

        Util.setTypeFace(getApplicationContext(), txtNewExpenseTitle, "Quicksand.otf");
    }
}

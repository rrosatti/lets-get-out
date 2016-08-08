package com.example.rodri.letsgetout.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.example.rodri.letsgetout.model.Saving;
import com.example.rodri.letsgetout.util.Util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by rodri on 7/18/2016.
 */
public class NewSavingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etDescription;
    private EditText etValue;
    private Button btSetDate;
    private Button btConfirm;
    private TextView txtNewSavingTitle;

    private int day = 0, month = 0, year = 0;

    private MyDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_saving);

        toolbar = (Toolbar) findViewById(R.id.toolbarNewSaving);
        etDescription = (EditText) findViewById(R.id.etSavingDescription);
        etValue = (EditText) findViewById(R.id.etSavingValue);
        btSetDate = (Button) findViewById(R.id.newSaving_btSetDate);
        btConfirm = (Button) findViewById(R.id.newSaving_btConfirm);
        txtNewSavingTitle = (TextView) findViewById(R.id.txtNewSaving);

        // set custom type face to the EditTexts, Buttons and TextViews
        setStyle();

        dataSource = new MyDataSource(this);

        toolbar.setTitle(R.string.toolbar_new_saving);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewSavingActivity.this, R.style.AppTheme, dateSetListener,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();

            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etDescription.getText().toString();
                String value = etValue.getText().toString();

                if (description.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.toast_description_field_empty, Toast.LENGTH_SHORT).show();
                    return;
                } else if (value.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.toast_value_field_empty, Toast.LENGTH_SHORT).show();
                    return;
                } else if (day == 0){
                    Toast.makeText(getApplicationContext(), R.string.toast_set_a_date, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    try {
                        dataSource.open();

                        Saving newSaving = dataSource.createSaving(description, Float.valueOf(value), day, month, year);
                        if (dataSource.isThereAlreadyAMonthlyBalance(month, year)) {
                            dataSource.addSavingToTheMonthlyBalance(month, year, Float.valueOf(value));
                        } else {
                            dataSource.createMonthlyBalance(month, year, 0, Float.valueOf(value), Float.valueOf(value));
                        }
                        Toast.makeText(getApplicationContext(), R.string.toast_new_saving_created, Toast.LENGTH_SHORT).show();
                        dataSource.close();

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", newSaving);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();

                    } catch (Exception e) {
                        dataSource.close();
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void setStyle() {
        Util.setTypeFace(getApplicationContext(), etDescription, "Quicksand-Italic.otf");
        Util.setTypeFace(getApplicationContext(), etValue, "Quicksand-Italic.otf");

        Util.setTypeFace(getApplicationContext(), btConfirm, "Quicksand.otf");
        Util.setTypeFace(getApplicationContext(), btSetDate, "Quicksand.otf");

        Util.setTypeFace(getApplicationContext(), txtNewSavingTitle, "Quicksand.otf");
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}

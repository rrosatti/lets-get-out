package com.example.rodri.letsgetout.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.database.MyDataSource;
import com.example.rodri.letsgetout.model.Expense;
import com.example.rodri.letsgetout.model.GenericBudget;
import com.example.rodri.letsgetout.model.Saving;
import com.example.rodri.letsgetout.util.Util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by rodri on 8/9/2016.
 */
public class UpdateGenericBudgetActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etValue;
    private EditText etDescription;
    private TextView txtDate;
    private Button btChangeDate;
    private Button btUpdate;

    private GenericBudget genericBudget;
    private int day, month, year;

    // Those variables will be used to check if the user actually updated something when clicking in btUpdate
    private String oldDescription, oldValue, oldDate;

    private MyDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_generic_budget);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            genericBudget = (GenericBudget) extras.getSerializable("generic_budget");
            day = genericBudget.getDay();
            month = genericBudget.getMonth();
            year = genericBudget.getYear();
            oldDate = String.valueOf(day + "/" + month + "/" + year);
            if (genericBudget instanceof Expense) {
                oldDescription = ((Expense) genericBudget).getName();
            } else {
                oldDescription = ((Saving) genericBudget).getDescription();
            }
            oldValue = String.valueOf(genericBudget.getValue());

        } else {
            Log.e("ERROR", "ERROR! There is no content in the Intent!");
            finish();
        }

        // Find views in the layout and set custom style(font)
        initializeViews();
        setStyleToTheViews();

        // Check whether generic budget is an instance of Expense or Saving, in order to
        // set the proper title in the toolbar
        if (genericBudget instanceof Expense) {
            toolbar.setTitle(R.string.toolbar_update_expense);
        } else {
            toolbar.setTitle(R.string.toolbar_update_saving);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        etValue.setText(String.valueOf(genericBudget.getValue()));
        txtDate.setText(day+"/"+month+"/"+year);
        // Check whether generic budget is an instance of Expense or Saving, ir order to show the proper
        // content in the EditText
        if (genericBudget instanceof Expense) {
            etDescription.setText(((Expense) genericBudget).getName());
        } else {
            etDescription.setText(((Saving) genericBudget).getDescription());
        }

        btChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int yeaR, int monthOfYear, int dayOfMonth) {
                        day = dayOfMonth;
                        month = monthOfYear + 1;
                        year = yeaR;
                        txtDate.setText(day+"/"+month+"/"+year);
                    }
                };

                // Show the corresponding  generic budget's date
                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                cal.set(year, month - 1, day);
                DatePickerDialog dialog = new DatePickerDialog(UpdateGenericBudgetActivity.this, R.style.AppTheme, listener,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String value = etValue.getText().toString();
                String description = etDescription.getText().toString();
                String date = txtDate.getText().toString();

                // Check whether user updated something or not
                if (value.equals(oldValue) && description.equals(oldDescription) && date.equals(oldDate)) {
                    Toast.makeText(getApplicationContext(), R.string.toast_anything_was_updated, Toast.LENGTH_SHORT).show();
                    return;
                }

                // check if the description, value or date EditTexts are not empty
                if (value.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.toast_value_field_empty, Toast.LENGTH_SHORT).show();
                } else if (description.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.toast_description_field_empty, Toast.LENGTH_SHORT).show();
                } else if (date.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.toast_set_a_date, Toast.LENGTH_SHORT).show();
                } else {
                    dataSource = new MyDataSource(getApplicationContext());

                    try {
                        dataSource.open();
                        if (genericBudget instanceof Expense) {
                            dataSource.updateExpense(((Expense) genericBudget).getId(), description,
                                    Float.valueOf(value), day, month, year);
                            Toast.makeText(getApplicationContext(), R.string.toast_expense_updated, Toast.LENGTH_SHORT).show();
                        } else {
                            dataSource.updateSaving(((Saving) genericBudget).getId(), description,
                                    Float.valueOf(value), day, month, year);
                            Toast.makeText(getApplicationContext(), R.string.toast_saving_updated, Toast.LENGTH_SHORT).show();
                        }
                        dataSource.close();

                    } catch (Exception e) {
                        dataSource.close();
                        e.printStackTrace();
                    }

                    setResult(RESULT_OK);
                    finish();
                }

            }
        });
    }

    public void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarUpdateGenericBudget);
        etValue = (EditText) findViewById(R.id.etValue);
        etDescription = (EditText) findViewById(R.id.etDescription);
        txtDate = (TextView) findViewById(R.id.updateGenericBudget_txtDate);
        btChangeDate = (Button) findViewById(R.id.btChangeDate);
        btUpdate = (Button) findViewById(R.id.btUpdateGenericBudget);
    }

    public void setStyleToTheViews() {
        Util.setTypeFace(getApplicationContext(), etValue, "Quicksand-Italic.otf");
        Util.setTypeFace(getApplicationContext(), etDescription, "Quicksand-Italic.otf");
        Util.setTypeFace(getApplicationContext(), txtDate, "Quicksand-Italic.otf");
        Util.setTypeFace(getApplicationContext(), btChangeDate, "Quicksand.otf");
        Util.setTypeFace(getApplicationContext(), btUpdate, "Quicksand.otf");
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}

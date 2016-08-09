package com.example.rodri.letsgetout.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.model.Expense;
import com.example.rodri.letsgetout.model.GenericBudget;
import com.example.rodri.letsgetout.model.Saving;
import com.example.rodri.letsgetout.util.Util;

/**
 * Created by rodri on 8/9/2016.
 */
public class UpdateGenericBudgetActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etValue;
    private EditText etDescription;
    private EditText etDate;
    private Button btChangeDate;
    private Button btUpdate;

    private GenericBudget genericBudget;
    private int day, month, year;

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
        } else {
            Log.e("ERROR", "ERROR! There is no content in the Intent!");
            finish();
        }

        initializeViews();
        setStyleToTheViews();

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
                onBackPressed();
                // change it later (refresh list view???)
            }
        });

        etValue.setText(String.valueOf(genericBudget.getValue()));
        etDate.setText(day+"/"+month+"/"+year);
        if (genericBudget instanceof Expense) {
            etDescription.setText(((Expense) genericBudget).getName());
        } else {
            etDescription.setText(((Saving) genericBudget).getDescription());
        }
    }

    public void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbarUpdateGenericBudget);
        etValue = (EditText) findViewById(R.id.etValue);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etDate = (EditText) findViewById(R.id.etDate);
        btChangeDate = (Button) findViewById(R.id.btChangeDate);
        btUpdate = (Button) findViewById(R.id.btUpdateGenericBudget);
    }

    public void setStyleToTheViews() {
        Util.setTypeFace(getApplicationContext(), etValue, "Quicksand-Italic.otf");
        Util.setTypeFace(getApplicationContext(), etDescription, "Quicksand-Italic.otf");
        Util.setTypeFace(getApplicationContext(), etDate, "Quicksand-Italic.otf");
        Util.setTypeFace(getApplicationContext(), btChangeDate, "Quicksand.otf");
        Util.setTypeFace(getApplicationContext(), btUpdate, "Quicksand.otf");
    }
}

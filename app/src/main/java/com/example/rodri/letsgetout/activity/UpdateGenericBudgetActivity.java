package com.example.rodri.letsgetout.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.model.GenericBudget;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_generic_budget);

        toolbar = (Toolbar) findViewById(R.id.toolbarUpdateGenericBudget);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                // change it later (refresh list view???)
            }
        });
    }
}

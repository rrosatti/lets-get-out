package com.example.rodri.letsgetout.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.activity.NewExpenseActivity;
import com.example.rodri.letsgetout.activity.NewSavingActivity;
import com.example.rodri.letsgetout.adapter.GenericBudgetAdapter;
import com.example.rodri.letsgetout.database.MyDataSource;
import com.example.rodri.letsgetout.model.GenericBudget;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 7/10/2016.
 */
public class ExpensesAndSavingsFragment extends Fragment {

    private static final String STATE_ITEMS_LIST = "items";

    private FloatingActionButton newExpense;
    private FloatingActionButton newSaving;
    private ListView listOfExpensesAndSavings;
    private ProgressBar progressBar;

    private GenericBudgetAdapter adapter;
    private MyDataSource dataSource;

    private List<GenericBudget> expensesAndSavings;

    public ExpensesAndSavingsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_expenses_and_savings, null);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        newExpense = (FloatingActionButton) view.findViewById(R.id.fabNewExpense);
        newSaving = (FloatingActionButton) view.findViewById(R.id.fabNewSaving);
        listOfExpensesAndSavings = (ListView) view.findViewById(R.id.listOfExpensesAndSavings);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarExpensesAndSavings);

        expensesAndSavings = new ArrayList<>();
        dataSource = new MyDataSource(getActivity());

        if (savedInstanceState != null) {
            expensesAndSavings = (ArrayList<GenericBudget>) savedInstanceState.getSerializable(STATE_ITEMS_LIST);
            adapter = new GenericBudgetAdapter(getActivity(), 0, expensesAndSavings, ExpensesAndSavingsFragment.this);
            listOfExpensesAndSavings.setAdapter(adapter);
        } else {
            GetDataFromDatabase task = new GetDataFromDatabase();
            task.execute("");
        }


        newExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNewExpense = new Intent(getActivity(), NewExpenseActivity.class);
                startActivityForResult(intentNewExpense, 1);
            }
        });

        newSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNewSaving = new Intent(getActivity(), NewSavingActivity.class);
                startActivityForResult(intentNewSaving, 1);
            }
        });
    }

    private class GetDataFromDatabase extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(1000);
                dataSource.open();

                expensesAndSavings = dataSource.getAllExpensesAndSavings();

                dataSource.close();

            } catch (Exception e) {
                dataSource.close();
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            if (expensesAndSavings != null) {
                adapter = new GenericBudgetAdapter(getActivity(), 0, expensesAndSavings, ExpensesAndSavingsFragment.this);
                listOfExpensesAndSavings.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(STATE_ITEMS_LIST, (Serializable) expensesAndSavings);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                GenericBudget newGenericBudget = (GenericBudget) data.getSerializableExtra("result");
                if (listOfExpensesAndSavings != null && adapter != null) {

                    adapter.add(newGenericBudget);
                    adapter.notifyDataSetChanged();
                } else {
                    GetDataFromDatabase task = new GetDataFromDatabase();
                    task.execute("");
                }
            }
        } else if (requestCode == 2) {
            Toast.makeText(getActivity(), "HEY!", Toast.LENGTH_SHORT).show();
            adapter.onActivityResult(requestCode, resultCode, data);
        }
    }
}

package com.example.rodri.letsgetout.fragment;


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
import android.widget.Toast;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.activity.NewExpenseActivity;
import com.example.rodri.letsgetout.activity.NewSavingActivity;
import com.example.rodri.letsgetout.adapter.GenericBudgetAdapter;
import com.example.rodri.letsgetout.database.MyDataSource;
import com.example.rodri.letsgetout.model.GenericBudget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 7/10/2016.
 */
public class ExpensesAndSavingsFragment extends Fragment {

    private FloatingActionButton newExpense;
    private FloatingActionButton newSaving;
    private ListView listOfExpensesAndSavings;

    private GenericBudgetAdapter adapter;
    private MyDataSource dataSource;

    private List<GenericBudget> expensesAndSavings;

    public ExpensesAndSavingsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_expenses_and_savings, null);

        // Try to find a way to perform the db operations in background (AsyncTasks, Loaders?)

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        newExpense = (FloatingActionButton) view.findViewById(R.id.fabNewExpense);
        newSaving = (FloatingActionButton) view.findViewById(R.id.fabNewSaving);
        listOfExpensesAndSavings = (ListView) view.findViewById(R.id.listOfExpensesAndSavings);

        expensesAndSavings = new ArrayList<>();
        dataSource = new MyDataSource(getActivity());

        GetDataFromDatabase task = new GetDataFromDatabase();
        task.execute("");


        newExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNewExpense = new Intent(getActivity(), NewExpenseActivity.class);
                startActivity(intentNewExpense);
            }
        });

        newSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNewSaving = new Intent(getActivity(), NewSavingActivity.class);
                startActivity(intentNewSaving);
            }
        });
    }

    private class GetDataFromDatabase extends AsyncTask<String, Void, String> {

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
            if (!expensesAndSavings.isEmpty()) {
                adapter = new GenericBudgetAdapter(getActivity(), 0, expensesAndSavings);
                listOfExpensesAndSavings.setAdapter(adapter);
            }
        }
    }
}

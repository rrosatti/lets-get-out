package com.example.rodri.letsgetout.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rodri.letsgetout.R;

/**
 * Created by rodri on 7/10/2016.
 */
public class ExpensesAndSavingsFragment extends Fragment {

    private FloatingActionButton newExpense;
    private FloatingActionButton newSaving;

    public ExpensesAndSavingsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_expenses, null);

        newExpense = (FloatingActionButton) v.findViewById(R.id.fabNewExpense);
        newSaving = (FloatingActionButton) v.findViewById(R.id.fabNewSaving);

        newExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "New Expense", Toast.LENGTH_SHORT).show();
            }
        });

        newSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "New Saving", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}

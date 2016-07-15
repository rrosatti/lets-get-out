package com.example.rodri.portugalproject.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rodri.portugalproject.R;

/**
 * Created by rodri on 7/10/2016.
 */
public class ExpensesAndSavingsFragment extends Fragment {

    private ImageView newExpense;

    public ExpensesAndSavingsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_expenses, null);

       /** newExpense = (ImageView) v.findViewById(R.id.fabAddExpense);
        newExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "This is just a test!", Toast.LENGTH_SHORT).show();
            }
        });*/

        return v;
    }

}

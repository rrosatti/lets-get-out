package com.example.rodri.portugalproject.fragment;

import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rodri.portugalproject.R;
import com.example.rodri.portugalproject.database.MyDataSource;
import com.example.rodri.portugalproject.model.GenericBudget;

import java.util.List;

/**
 * Created by rodri on 7/13/2016.
 */
public class CurrentBalanceFragment extends Fragment {

    private MyDataSource myDataSource;

    public CurrentBalanceFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //View v = inflater.inflate(R.layout.fragment_current_balance, null);
        View v = null;

        myDataSource = new MyDataSource(getActivity());

        try {
            myDataSource.open();

            List<GenericBudget> genericBudgets = myDataSource.getAllExpensesAndSavings();

            if (genericBudgets == null) {
                v = inflater.inflate(R.layout.fragment_new_goal, null);
            } else {
                v = inflater.inflate(R.layout.fragment_current_balance, null);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return v;

    }
}

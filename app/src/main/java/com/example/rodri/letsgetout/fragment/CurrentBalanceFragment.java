package com.example.rodri.letsgetout.fragment;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.activity.SetUpGoalActivity;
import com.example.rodri.letsgetout.database.MyDataSource;
import com.example.rodri.letsgetout.model.GenericBudget;

import java.util.List;

/**
 * Created by rodri on 7/13/2016.
 */
public class CurrentBalanceFragment extends Fragment {

    private MyDataSource myDataSource;
    private Button btSetUpGoal;

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
                btSetUpGoal = (Button) v.findViewById(R.id.btSetUpGoal);

                btSetUpGoal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentSetUpGoal = new Intent(getActivity(), SetUpGoalActivity.class);
                        startActivity(intentSetUpGoal);
                    }
                });

            } else {
                v = inflater.inflate(R.layout.fragment_current_balance, null);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return v;

    }
}

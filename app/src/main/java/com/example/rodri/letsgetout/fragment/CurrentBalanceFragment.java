package com.example.rodri.letsgetout.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.activity.SetUpGoalActivity;
import com.example.rodri.letsgetout.activity.UpdateGoalActivity;
import com.example.rodri.letsgetout.database.MyDataSource;
import com.example.rodri.letsgetout.model.CurrentBalance;
import com.example.rodri.letsgetout.model.GenericBudget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by rodri on 7/13/2016.
 */
public class CurrentBalanceFragment extends Fragment {

    private MyDataSource myDataSource;

    // no data found corresponding to current balance
    private Button btSetUpGoal;

    private TextView txtEstimatedValue;
    private TextView txtAchievedValue;
    private TextView txtNeedToSave;
    private TextView txtMonthsRemaining;
    private Button btUpdateGoal;

    private int day, month, year;

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
            CurrentBalance cBalance = myDataSource.getCurrentBalance(1);

            if (cBalance == null) {
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
                txtEstimatedValue = (TextView) v.findViewById(R.id.txtEstimatedValue);
                txtAchievedValue = (TextView) v.findViewById(R.id.txtAchievedValue);
                txtNeedToSave = (TextView) v.findViewById(R.id.txtNeedToSave);
                txtMonthsRemaining = (TextView) v.findViewById(R.id.txtMonthsRemaining);
                btUpdateGoal = (Button) v.findViewById(R.id.btUpdateGoal);

                final CurrentBalance currentBalance = myDataSource.getCurrentBalance(1);
                txtEstimatedValue.setText("R$ " + String.valueOf(currentBalance.getEstimatedValue()));
                txtAchievedValue.setText("R$ " + String.valueOf(currentBalance.getAchievedValue()));
                txtNeedToSave.setText("R$ " + String.valueOf(currentBalance.getEstimatedValue() - currentBalance.getAchievedValue()));

                int years = currentBalance.getYear() - Calendar.getInstance().get(Calendar.YEAR);
                int months = currentBalance.getMonth() - Calendar.getInstance().get(Calendar.MONTH);
                int monthsRemaining = (years * 12) + months;

                txtMonthsRemaining.setText(String.valueOf(monthsRemaining));

                btUpdateGoal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), UpdateGoalActivity.class);
                        startActivity(intent);
                    }
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return v;

    }
}

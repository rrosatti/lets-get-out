package com.example.rodri.letsgetout.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.activity.SetUpGoalActivity;
import com.example.rodri.letsgetout.activity.UpdateGoalActivity;
import com.example.rodri.letsgetout.database.MyDataSource;
import com.example.rodri.letsgetout.model.CurrentBalance;
import com.example.rodri.letsgetout.util.Util;

import java.util.Calendar;

/**
 * Created by rodri on 7/13/2016.
 */
public class CurrentBalanceFragment extends Fragment {

    private static final String STATE_CURRENT_BALANCE = "items";

    private MyDataSource myDataSource;

    // no data found corresponding to current balance
    private Button btSetUpGoal;

    private TextView txtEstimatedValueLabel;
    private TextView txtAchievedValueLabel;
    private TextView txtNeedToSaveLabel;
    private TextView txtMonthsRemainingLabel;
    private TextView txtEstimatedValue;
    private TextView txtAchievedValue;
    private TextView txtNeedToSave;
    private TextView txtMonthsRemaining;
    private Button btUpdateGoal;
    private ProgressBar progressBar;

    private int day, month, year;

    private CurrentBalance currentBalance;

    public CurrentBalanceFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //View v = inflater.inflate(R.layout.fragment_current_balance, null);
        View v = null;

        myDataSource = new MyDataSource(getActivity());

        try {
            myDataSource.open();

            //it was here


            if (!myDataSource.isThereAnyCurrentBalance()) {
                v = inflater.inflate(R.layout.fragment_new_goal, null);
                btSetUpGoal = (Button) v.findViewById(R.id.btSetUpGoal);
                Util.setTypeFace(getContext(), btSetUpGoal, "Quicksand.otf");

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
                progressBar = (ProgressBar) v.findViewById(R.id.progressBarCurrentBalance);

                // Find Views regarding to the labels
                txtEstimatedValueLabel = (TextView) v.findViewById(R.id.txtEstimatedValueLabel);
                txtAchievedValueLabel = (TextView) v.findViewById(R.id.txtAchievedValueLabel);
                txtNeedToSaveLabel = (TextView) v.findViewById(R.id.txtNeedToSaveLabel);
                txtMonthsRemainingLabel = (TextView) v.findViewById(R.id.txtMonthsRemainingLabel);

                // setTypeFace for all text views
                setStyle();

                if (savedInstanceState != null) {
                    currentBalance = (CurrentBalance) savedInstanceState.getSerializable(STATE_CURRENT_BALANCE);

                    txtEstimatedValue.setText("R$ " + String.valueOf(currentBalance.getEstimatedValue()));
                    txtAchievedValue.setText("R$ " + String.valueOf(currentBalance.getAchievedValue()));
                    txtNeedToSave.setText("R$ " + String.valueOf(currentBalance.getEstimatedValue() - currentBalance.getAchievedValue()));

                    int years = currentBalance.getYear() - Calendar.getInstance().get(Calendar.YEAR);
                    int months = currentBalance.getMonth() - Calendar.getInstance().get(Calendar.MONTH);
                    int monthsRemaining = (years * 12) + months;

                    txtMonthsRemaining.setText(String.valueOf(monthsRemaining));
                } else {
                    GetDataFromDatabase task = new GetDataFromDatabase();
                    task.execute("");
                }


                btUpdateGoal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), UpdateGoalActivity.class);
                        startActivityForResult(intent, 1);
                    }
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return v;

    }

    public void setStyle() {
        Util.setTypeFace(getContext(), txtEstimatedValue, "Quicksand.otf");
        Util.setTypeFace(getContext(), txtAchievedValue, "Quicksand.otf");
        Util.setTypeFace(getContext(), txtNeedToSave, "Quicksand.otf");
        Util.setTypeFace(getContext(), txtMonthsRemaining, "Quicksand.otf");

        Util.setTypeFace(getContext(), txtEstimatedValueLabel, "Quicksand-Bold.otf");
        Util.setTypeFace(getContext(), txtAchievedValueLabel, "Quicksand-Bold.otf");
        Util.setTypeFace(getContext(), txtNeedToSaveLabel, "Quicksand-Bold.otf");
        Util.setTypeFace(getContext(), txtMonthsRemainingLabel, "Quicksand-Bold.otf");

        Util.setTypeFace(getContext(), btUpdateGoal, "Quicksand.otf");
    }

    private class GetDataFromDatabase extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            changeVisibility(0);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(500);

                currentBalance = myDataSource.getCurrentBalance(1);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);

            changeVisibility(1);
            txtEstimatedValue.setText("R$ " + String.valueOf(currentBalance.getEstimatedValue()));
            txtAchievedValue.setText("R$ " + String.valueOf(currentBalance.getAchievedValue()));
            txtNeedToSave.setText("R$ " + String.valueOf(currentBalance.getEstimatedValue() - currentBalance.getAchievedValue()));

            int years = currentBalance.getYear() - Calendar.getInstance().get(Calendar.YEAR);
            int months = currentBalance.getMonth() - Calendar.getInstance().get(Calendar.MONTH);
            int monthsRemaining = (years * 12) + months;

            txtMonthsRemaining.setText(String.valueOf(monthsRemaining));
        }
    }

    /**
     * if       @param visibility == 0, set as View.GONE
     * else if  @param visibility == 1, set as View.VISIBLE
     * @param visibility
     */
    public void changeVisibility(int visibility) {
        if (visibility == 0) {
            txtEstimatedValueLabel.setVisibility(View.GONE);
            txtAchievedValueLabel.setVisibility(View.GONE);
            txtNeedToSaveLabel.setVisibility(View.GONE);
            txtMonthsRemainingLabel.setVisibility(View.GONE);
            btUpdateGoal.setVisibility(View.GONE);
        } else if (visibility == 1) {
            txtEstimatedValueLabel.setVisibility(View.VISIBLE);
            txtAchievedValueLabel.setVisibility(View.VISIBLE);
            txtNeedToSaveLabel.setVisibility(View.VISIBLE);
            txtMonthsRemainingLabel.setVisibility(View.VISIBLE);
            btUpdateGoal.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(STATE_CURRENT_BALANCE, currentBalance);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                new GetDataFromDatabase().execute();
            }
        }
    }
}

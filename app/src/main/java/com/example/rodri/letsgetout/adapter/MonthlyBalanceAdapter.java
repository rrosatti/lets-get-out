package com.example.rodri.letsgetout.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.model.MonthlyBalance;
import com.example.rodri.letsgetout.util.Util;

import java.util.List;

/**
 * Created by rodri on 8/4/2016.
 */
public class MonthlyBalanceAdapter extends ArrayAdapter<MonthlyBalance> {

    private Activity activity;
    private List<MonthlyBalance> monthlyBalances;
    private LayoutInflater inflater = null;
    private String[] months;

    public MonthlyBalanceAdapter(Activity activity, int textViewResourceId, List<MonthlyBalance> monthlyBalances) {
        super(activity, textViewResourceId, monthlyBalances);
        try {
            this.activity = activity;
            this.monthlyBalances = monthlyBalances;
            this.months = activity.getResources().getStringArray(R.array.months);

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return monthlyBalances.size();
    }

    public class ViewHolder {
        public TextView displayMonthAndYear;
        public TextView displayMonthlyBalance;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.monthly_balance_item_list, null);

            holder.displayMonthAndYear = (TextView) v.findViewById(R.id.txtMonthAndYear);
            holder.displayMonthlyBalance = (TextView) v.findViewById(R.id.txtMonthlyBalance);

            v.setTag(holder);
        } else {
            inflater = (LayoutInflater) v.getTag();
        }

        MonthlyBalance mb = monthlyBalances.get(position);
        holder.displayMonthAndYear.setText(months[mb.getMonth()]+"/"+mb.getYear());
        holder.displayMonthlyBalance.setText("R$ " + Util.setNumberFormat(mb.getBalance()));

        return v;
    }
}

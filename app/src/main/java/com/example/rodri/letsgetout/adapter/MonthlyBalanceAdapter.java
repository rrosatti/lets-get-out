package com.example.rodri.letsgetout.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.activity.MonthlyBalanceGraphActivity;
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
            holder = (ViewHolder) v.getTag();
        }

        final MonthlyBalance mb = monthlyBalances.get(position);
        holder.displayMonthAndYear.setText(months[mb.getMonth() - 1]+"/"+mb.getYear());
        holder.displayMonthlyBalance.setText("R$ " + Util.setNumberFormat(mb.getBalance()));

        // set custom font
        Util.setTypeFace(activity, holder.displayMonthAndYear, "Quicksand-Bold.otf");
        Util.setTypeFace(activity, holder.displayMonthlyBalance, "Quicksand.otf");

        // set background color according to the monthly balance
        if (mb.getBalance() > 0) {
            holder.displayMonthlyBalance.setBackgroundColor(activity.getResources().getColor(R.color.green));
        } else {
            holder.displayMonthlyBalance.setBackgroundColor(activity.getResources().getColor(R.color.red));
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, MonthlyBalanceGraphActivity.class);
                i.putExtra("monthly_balance_id", mb.getId());
                activity.startActivity(i);
            }
        });

        return v;
    }
}

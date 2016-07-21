package com.example.rodri.letsgetout.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.model.Expense;

import java.util.List;

/**
 * Created by rodri on 7/12/2016.
 */
public class ExpenseAdapter extends ArrayAdapter<Expense> {

    private Activity activity;
    private List<Expense> expenses;
    private LayoutInflater inflater = null;

    public ExpenseAdapter(Activity activity, int textViewResourceId, List<Expense> expenses) {
        super(activity, textViewResourceId, expenses);
        try {
            this.activity = activity;
            this.expenses = expenses;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return expenses.size();
    }

    public class ViewHolder {
        public TextView displayDate;
        public TextView displayTitle;
        public TextView displayValue;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.expense_and_saving_item_list, null);

            holder.displayDate = (TextView) v.findViewById(R.id.txtDate);
            holder.displayTitle = (TextView) v.findViewById(R.id.txtDescription);
            holder.displayValue = (TextView) v.findViewById(R.id.txtValue);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        Expense expense = expenses.get(position);

        holder.displayTitle.setText(expense.getName());
        holder.displayValue.setText("R$ " + String.valueOf(expense.getValue()));
        holder.displayDate.setText(expense.getDay() + "/" + expense.getMonth() + "/" + expense.getYear());

        return v;
    }
}

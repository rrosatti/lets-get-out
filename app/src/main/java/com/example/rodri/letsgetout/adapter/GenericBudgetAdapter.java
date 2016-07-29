package com.example.rodri.letsgetout.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.model.Expense;
import com.example.rodri.letsgetout.model.GenericBudget;
import com.example.rodri.letsgetout.model.Saving;
import com.example.rodri.letsgetout.util.Util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by rodri on 7/21/2016.
 */
public class GenericBudgetAdapter extends ArrayAdapter<GenericBudget> {

    private Activity activity;
    private List<GenericBudget> genericBudgets;
    private LayoutInflater inflater = null;

    public GenericBudgetAdapter(Activity activity, int textViewResourceId, List<GenericBudget> genericBudgets) {
        super(activity, textViewResourceId, genericBudgets);
        try {
            this.activity = activity;
            this.genericBudgets = genericBudgets;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return genericBudgets.size();
    }

    public class ViewHolder {
        public TextView displayDate;
        public TextView displayDescription;
        public TextView displayValue;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.expense_and_saving_item_list, null);

            holder.displayDate = (TextView) v.findViewById(R.id.txtDate);
            holder.displayDescription = (TextView) v.findViewById(R.id.txtDescription);
            holder.displayValue = (TextView) v.findViewById(R.id.txtValue);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        GenericBudget genericBudget = genericBudgets.get(position);

        holder.displayDate.setText(genericBudget.getDay()+"/"+genericBudget.getMonth()+"/"+genericBudget.getYear());

        String description = "";
        if (genericBudget instanceof Expense) {
            description = ((Expense) genericBudget).getName();
            holder.displayValue.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.fab_red));

        } else if (genericBudget instanceof Saving) {
            description = ((Saving) genericBudget).getDescription();
            holder.displayValue.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.fab_green));
        }

        holder.displayDescription.setText(description);

        NumberFormat formatter = new DecimalFormat("#0.00");
        holder.displayValue.setText("R$ " + String.valueOf(formatter.format(genericBudget.getValue())));

        // set custom type font to the TextViews
        Util.setTypeFace(getContext(), holder.displayDate, "Quicksand-Bold.otf");
        Util.setTypeFace(getContext(), holder.displayDescription, "Quicksand.otf");
        Util.setTypeFace(getContext(), holder.displayValue, "Quicksand.otf");

        return v;
    }

}

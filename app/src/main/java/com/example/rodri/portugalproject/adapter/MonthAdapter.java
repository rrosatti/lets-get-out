package com.example.rodri.portugalproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rodri.portugalproject.R;
import com.example.rodri.portugalproject.model.Month;

import java.util.List;

/**
 * Created by rodri on 7/11/2016.
 */
public class MonthAdapter extends ArrayAdapter<Month> {

    private Activity activity;
    private List<Month> months;
    private LayoutInflater inflater = null;

    public MonthAdapter(Activity activity, int textViewResourceId, List<Month> months) {
        super(activity, textViewResourceId, months);
        try {
            this.activity = activity;
            this.months = months;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return months.size();
    }

    public class ViewHolder {
        public TextView displayTitle;
        public TextView displayTotal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.month_item_list, null);

            holder.displayTitle = (TextView) v.findViewById(R.id.txtMonthTitle);
            holder.displayTotal = (TextView) v.findViewById(R.id.txtTotal);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.displayTitle.setText(months.get(position).getTitle());
        holder.displayTotal.setText("R$ " + String.valueOf(months.get(position).getTotal()));

        return v;
    }
}

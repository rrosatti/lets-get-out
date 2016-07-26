package com.example.rodri.letsgetout.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.model.StatisticsMenuItem;

import java.util.List;

/**
 * Created by rodri on 7/26/2016.
 */
public class StatisticsMenuItemAdapter extends RecyclerView.Adapter<StatisticsMenuItemAdapter.MyViewHolder> {

    private Activity activity;
    private List<StatisticsMenuItem> menuItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView displayIcon;
        public TextView displayTitle;

        public MyViewHolder(View v) {
            super(v);

            displayIcon = (ImageView) v.findViewById(R.id.imgStatisticsOptionIcon);
            displayTitle = (TextView) v.findViewById(R.id.txtStatisticsOptionTitle);
        }
    }

    public StatisticsMenuItemAdapter(Activity activity, List<StatisticsMenuItem> menuItems) {
        this.activity = activity;
        this.menuItems = menuItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card, parent, false);

        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StatisticsMenuItem menuItem = menuItems.get(position);

        holder.displayIcon.setImageDrawable(menuItem.getIcon());
        holder.displayTitle.setText(menuItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }
}

package com.example.rodri.letsgetout.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.activity.GraphActivity;
import com.example.rodri.letsgetout.activity.SimulationActivity;
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
        public StatisticsMenuItem currentMenuItem;

        public MyViewHolder(View v) {
            super(v);

            displayIcon = (ImageView) v.findViewById(R.id.imgStatisticsOptionIcon);
            displayTitle = (TextView) v.findViewById(R.id.txtStatisticsOptionTitle);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open the proper Activity according to the selected item (drawable ID)
                    if (currentMenuItem.getIconId() == R.drawable.ic_simulation) {
                        Intent i = new Intent(activity, SimulationActivity.class);
                        activity.startActivity(i);
                    } else if (currentMenuItem.getIconId() == R.drawable.ic_graph) {
                        Intent i = new Intent(activity, GraphActivity.class);
                        activity.startActivity(i);
                    }
                }
            });
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
        final StatisticsMenuItem menuItem = menuItems.get(position);
        holder.currentMenuItem = menuItem;

        holder.displayIcon.setImageResource(menuItem.getIconId());
        holder.displayTitle.setText(menuItem.getTitle());

    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

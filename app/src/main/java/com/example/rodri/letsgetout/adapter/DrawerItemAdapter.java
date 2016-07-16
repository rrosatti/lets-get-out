package com.example.rodri.letsgetout.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.model.DrawerItem;

import java.util.List;

/**
 * Created by rodri on 7/8/2016.
 */
public class DrawerItemAdapter extends ArrayAdapter<DrawerItem> {

    private Activity activity;
    private List<DrawerItem> drawerItems;
    private LayoutInflater inflater = null;

    public DrawerItemAdapter(Activity activity, int textViewResourceId, List<DrawerItem> drawerItems) {
        super(activity, textViewResourceId, drawerItems);
        try {
            this.activity = activity;
            this.drawerItems = drawerItems;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return drawerItems.size();
    }

    public class ViewHolder {
        public ImageView displayMenuItemIcon;
        public TextView displayMenuItemTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            v = inflater.inflate(R.layout.drawer_list_item, null);

            holder.displayMenuItemIcon = (ImageView) v.findViewById(R.id.imgMenuItemIcon);
            holder.displayMenuItemTitle = (TextView) v.findViewById(R.id.txtMenuItemTitle);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.displayMenuItemIcon.setImageResource(drawerItems.get(position).getIcon());
        holder.displayMenuItemTitle.setText(drawerItems.get(position).getTitle());

        return v;
    }
}

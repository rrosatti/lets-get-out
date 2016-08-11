package com.example.rodri.letsgetout.fragment;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.adapter.StatisticsMenuItemAdapter;
import com.example.rodri.letsgetout.model.StatisticsMenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rodri on 7/25/2016.
 */
public class StatisticsFragment extends Fragment {

    private List<StatisticsMenuItem> menuItems;
    private StatisticsMenuItemAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TypedArray menuItemIcons;
    private List<String> menuItemTitles;

    public StatisticsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistics, null);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        createMenuItems();

        recyclerView = (RecyclerView) view.findViewById(R.id.statistics_recyclerView);

        adapter = new StatisticsMenuItemAdapter(getActivity(), menuItems);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

    }

    public void createMenuItems() {
        menuItems = new ArrayList<>();
        menuItemIcons = getResources().obtainTypedArray(R.array.statistics_icon_list);
        menuItemTitles = Arrays.asList(getResources().getStringArray(R.array.statistics_menu_item_title));

        for (int i = 0; i < menuItemTitles.size(); i++) {
            menuItems.add(new StatisticsMenuItem(i, menuItemTitles.get(i), menuItemIcons.getResourceId(i, -1)));
        }
    }
}

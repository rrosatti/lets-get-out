package com.example.rodri.letsgetout.fragment;

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
import java.util.List;

/**
 * Created by rodri on 7/25/2016.
 */
public class StatisticsFragment extends Fragment {

    private List<StatisticsMenuItem> menuItems;
    private StatisticsMenuItemAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

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

        menuItems.add(new StatisticsMenuItem(0, "Simulation", R.drawable.ic_simulation));
        menuItems.add(new StatisticsMenuItem(1, "Graph", R.drawable.ic_graph));
    }
}

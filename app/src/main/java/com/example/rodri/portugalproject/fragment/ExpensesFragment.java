package com.example.rodri.portugalproject.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rodri.portugalproject.R;
import com.example.rodri.portugalproject.adapter.ViewPagerAdapter;

/**
 * Created by rodri on 7/10/2016.
 */
public class ExpensesFragment extends Fragment {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ExpensesFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_expenses, null);

        toolbar = (Toolbar) v.findViewById(R.id.toolbarExpenses);
        viewPager = (ViewPager) v.findViewById(R.id.viewPagerExpenses);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) v.findViewById(R.id.tabLayoutExpenses);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPagerAdapter.addFragment(new YearFragment(2016), "2016");
        viewPagerAdapter.addFragment(new YearFragment(2017), "2017");
        viewPagerAdapter.addFragment(new YearFragment(2018), "2018");
        viewPager.setAdapter(viewPagerAdapter);
    }
}

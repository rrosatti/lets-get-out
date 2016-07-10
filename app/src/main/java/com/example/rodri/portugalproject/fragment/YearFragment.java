package com.example.rodri.portugalproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rodri.portugalproject.R;

/**
 * Created by rodri on 7/10/2016.
 */
public class YearFragment extends Fragment {

    private int year = 0;

    public YearFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (year == 0 && getArguments() != null) {
            this.year = getArguments().getInt("year");
        }

        View v = inflater.inflate(R.layout.months_list, null);

        // Here we must implement some methods to get the data relating to the given year and months

        return v;

    }
}

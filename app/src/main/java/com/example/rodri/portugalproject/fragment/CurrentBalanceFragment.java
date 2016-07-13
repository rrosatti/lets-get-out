package com.example.rodri.portugalproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rodri.portugalproject.R;

/**
 * Created by rodri on 7/13/2016.
 */
public class CurrentBalanceFragment extends Fragment {

    public CurrentBalanceFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_current_balance, null);

        return v;

    }
}

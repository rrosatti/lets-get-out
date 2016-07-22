package com.example.rodri.letsgetout.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rodri.letsgetout.R;
import com.example.rodri.letsgetout.util.Util;

import java.util.Random;

/**
 * Created by rodri on 7/7/2016.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        TextView quote = (TextView) rootView.findViewById(R.id.txtQuote);
        // get the quotes array located in strings.xml
        String[] quotesArray = getResources().getStringArray(R.array.quotes);

        // random a number from 0 to the length of quotesArray
        Random rand = new Random();
        int randomNumber = rand.nextInt(quotesArray.length);

        quote.setText(quotesArray[randomNumber]);
        Util.setTypeFace(getContext(), quote, "Bevan.ttf");

        return rootView;

    }
}

package com.example.rodri.letsgetout.util;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 *
 * This class will be used in PieChart to show a custom format to the numbers
 *
 * Created by rodri on 8/4/2016.
 */
public class CustomValueFormatter implements ValueFormatter {

    private DecimalFormat decimalFormat;

    public CustomValueFormatter() {
        decimalFormat = new DecimalFormat("###,###,##0.00");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
        return "R$ " + decimalFormat.format(value);
    }
}

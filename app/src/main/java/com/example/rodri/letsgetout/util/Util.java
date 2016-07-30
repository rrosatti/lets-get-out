package com.example.rodri.letsgetout.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by rodri on 7/7/2016.
 */
public class Util {

    public static void setFullScreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     *
     * @param context  - it will be used to getAssets()
     * @param textView
     * @param fontName
     */
    public static void setTypeFace(Context context, TextView textView, String fontName) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/"+fontName);
        textView.setTypeface(typeface);
    }

    public static void setTypeFace(Context context, Button bt, String fontName) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/"+fontName);
        bt.setTypeface(typeface);
    }

    public static String setNumberFormat(float value) {
        String format = "";
        if (value < 9999.99) {
            format = "#0.00";
        } else {
            format = "#0,000.00";
        }
        NumberFormat formatter = new DecimalFormat(format);
        return String.valueOf(formatter.format(value));
    }

}

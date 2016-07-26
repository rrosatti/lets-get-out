package com.example.rodri.letsgetout.model;

import android.graphics.drawable.Drawable;

/**
 * Created by rodri on 7/26/2016.
 */
public class StatisticsMenuItem {

    private long id;
    private String title;
    private Drawable icon;

    public StatisticsMenuItem() {}

    public StatisticsMenuItem(long id, String title, Drawable icon) {
        this.title = title;
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }


}

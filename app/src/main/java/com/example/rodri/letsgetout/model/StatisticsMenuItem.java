package com.example.rodri.letsgetout.model;

import android.graphics.drawable.Drawable;

/**
 * Created by rodri on 7/26/2016.
 */
public class StatisticsMenuItem {

    private long id;
    private String title;
    private int iconId;

    public StatisticsMenuItem() {}

    public StatisticsMenuItem(long id, String title, int iconId) {
        this.title = title;
        this.iconId = iconId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }


}

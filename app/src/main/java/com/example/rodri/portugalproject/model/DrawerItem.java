package com.example.rodri.portugalproject.model;

/**
 * Created by rodri on 7/6/2016.
 */
public class DrawerItem {

    private String title;
    private int icon;

    public DrawerItem() {}

    public DrawerItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

}

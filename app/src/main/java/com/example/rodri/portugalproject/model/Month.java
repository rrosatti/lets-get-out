package com.example.rodri.portugalproject.model;

/**
 * Created by rodri on 7/11/2016.
 */
public class Month {

    private long id;
    private String title;
    private float total;

    public Month() {}

    public Month(long id, String title, float total) {
        this.id = id;
        this.title = title;
        this.total = total;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

}

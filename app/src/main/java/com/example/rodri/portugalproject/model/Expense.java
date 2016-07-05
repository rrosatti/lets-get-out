package com.example.rodri.portugalproject.model;

import java.util.Date;

/**
 * Created by rodri on 7/5/2016.
 */
public class Expense {

    private long id;
    private String name;
    private float value;
    private Date date;

    public Expense() {}

    public Expense(long id, String name, float value, Date date) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}

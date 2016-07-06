package com.example.rodri.portugalproject.model;

import java.util.Date;

/**
 * Created by rodri on 7/5/2016.
 */
public class Expense {

    private long id;
    private String name;
    private float value;
    private int day;
    private int month;
    private int year;

    public Expense() {}

    public Expense(long id, String name, float value, int day, int month, int year) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.day = day;
        this.month = month;
        this.year = year;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int  getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}

package com.example.rodri.portugalproject.model;

/**
 * Created by rodri on 7/15/2016.
 */
public class GenericBudget {

    private float value;
    private int day;
    private int month;
    private int year;

    public GenericBudget() {}

    public GenericBudget(float value, int day, int month, int year) {
        this.value = value;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public float getValue() {
        return value;
    }

    public int getYear() {
        return year;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

}

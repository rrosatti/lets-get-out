package com.example.rodri.letsgetout.model;

/**
 * Created by rodri on 7/5/2016.
 */
public class CurrentBalance{

    private long id;
    private float estimatedValue;
    private float achievedValue;
    private int day;
    private int month;
    private int year;

    public CurrentBalance() {}

    public CurrentBalance(long id, float estimatedValue, float achievedValue, int day, int month, int year) {
        this.id = id;
        this.estimatedValue = estimatedValue;
        this.achievedValue = achievedValue;
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

    public float getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(float estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public float getAchievedValue() {
        return achievedValue;
    }

    public void setAchievedValue(float achievedValue) {
        this.achievedValue = achievedValue;
    }


    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

}
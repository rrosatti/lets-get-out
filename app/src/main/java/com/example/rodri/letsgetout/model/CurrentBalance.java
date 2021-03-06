package com.example.rodri.letsgetout.model;

import java.io.Serializable;

/**
 * Created by rodri on 7/5/2016.
 */
public class CurrentBalance implements Serializable{

    private long id;
    private double estimatedValue;
    private double achievedValue;
    private int day;
    private int month;
    private int year;

    public CurrentBalance() {}

    public CurrentBalance(double estimatedValue, double achievedValue, int day, int month, int year) {
        this.estimatedValue = estimatedValue;
        this.achievedValue = achievedValue;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public double calculateHowMuchNeedToSave() {
        return estimatedValue - achievedValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(double estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public double getAchievedValue() {
        return achievedValue;
    }

    public void setAchievedValue(double achievedValue) {
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

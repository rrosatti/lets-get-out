package com.example.rodri.portugalproject.model;

/**
 * Created by rodri on 7/14/2016.
 */
public class Saving {

    private long id;
    private String description;
    private float value;
    private int day;
    private int month;
    private int year;

    public Saving() {}

    public Saving(long id, float value, int day, int month, int year) {
        this.id = id;
        this.value = value;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Saving(long id, String description, float value, int day, int month, int year) {
        this(id, value, day, month, year);
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public float getValue() {
        return value;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
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

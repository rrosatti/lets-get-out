package com.example.rodri.letsgetout.model;

/**
 * Created by rodri on 8/3/2016.
 */
public class MonthlyBalance {

    private long id;
    private int month;
    private int year;
    private float totalExpenses;
    private float totalSavings;
    private float balance;

    public MonthlyBalance() {}

    public MonthlyBalance(long id, int month, int year, float totalExpenses, float totalSavings,  float balance) {
        this.id = id;
        this.month = month;
        this.year = year;
        this.totalExpenses = totalExpenses;
        this.totalSavings = totalSavings;
        this.balance = balance;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTotalSavings(float totalSavings) {
        this.totalSavings = totalSavings;
    }

    public void setTotalExpenses(float totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public float getTotalExpenses() {
        return totalExpenses;
    }

    public float getTotalSavings() {
        return totalSavings;
    }

    public int getMonth() {
        return month;
    }

    public float getBalance() {
        return balance;
    }


}

package com.example.rodri.portugalproject.model;

import java.util.Date;

/**
 * Created by rodri on 7/5/2016.
 */
public class Expense extends GenericBudget {

    private long id;
    private String name;

    public Expense() {}

    public Expense(long id, String name, float value, int day, int month, int year) {
        super(value, day, month, year);
        this.id = id;
        this.name = name;
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

}

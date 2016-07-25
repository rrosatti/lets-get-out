package com.example.rodri.letsgetout.model;

import java.io.Serializable;

/**
 * Created by rodri on 7/14/2016.
 */
public class Saving extends GenericBudget implements Serializable {

    private long id;
    private String description;

    public Saving() {}

    public Saving(long id, float value, int day, int month, int year) {
        super(value, day, month, year);
        this.id = id;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

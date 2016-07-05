package com.example.rodri.portugalproject.model;

/**
 * Created by rodri on 7/5/2016.
 */
public class CurrentBalance {

    private long id;
    private float estimatedValue;
    private float achievedValue;

    public CurrentBalance() {}

    public CurrentBalance(long id, float estimatedValue, float achievedValue) {
        this.id = id;
        this.estimatedValue = estimatedValue;
        this.achievedValue = achievedValue;
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

}

package com.example.labtest;

import java.io.Serializable;

public class Course implements Serializable {
    private String name;
    private int fee;
    private int duration;

    public Course(String name, int fee, int duration) {
        this.name = name;
        this.fee = fee;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

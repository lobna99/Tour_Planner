package com.example.tour_planner.model;

import java.io.Serializable;

public class Tour implements Serializable {
    private int id;
    private String name;
    private double duration;
    private String content;

    @Override
    public String toString() {
        return name;
    }
}

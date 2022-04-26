package com.example.tour_planner.model;



import java.io.Serializable;
import java.sql.Time;


public class Tour implements Serializable {
    private int id;
    private int transport_type;
    private String name;
    private String date;
    private String from;
    private String to;
    private Time duration;
    private double distance;
    private String content;

    public Tour(int id, int transport_type, String name, String date, String from, String to, Time duration, double distance, String content) {
        this.id = id;
        this.transport_type = transport_type;
        this.name = name;
        this.date = date;
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.distance = distance;
        this.content = content;
    }

}

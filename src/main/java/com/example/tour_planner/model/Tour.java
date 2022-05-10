package com.example.tour_planner.model;



import java.io.Serializable;
import java.sql.Time;


public class Tour implements Serializable {
    private int transport_type;
    private String name;
    private String date;
    private String from;
    private String to;
    private String duration;
    private double distance;
    private String content;

    public Tour( int transport_type, String name, String date, String from, String to, String duration, double distance, String content) {
        this.transport_type = transport_type;
        this.name = name;
        this.date = date;
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.distance = distance;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public double getDistance() {
        return distance;
    }

    public String getDuration() {
        return duration;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public int getTransport_type() {
        return transport_type;
    }

}

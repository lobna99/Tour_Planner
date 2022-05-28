package com.example.tour_planner.model;



import java.io.Serializable;


public class Tour implements Serializable {
    private String transport_type;
    private String name;
    private String from;
    private String to;
    private String duration;
    private double distance;
    private String content;

    public Tour(String transport_type, String name, String from, String to,String duration, double distance, String content) {
        this.transport_type = transport_type;
        this.name = name;
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


    public String getName() {
        return name;
    }

    public String getTransport_type() {
        return transport_type;
    }

    @Override
    public String toString() {
        return name;
    }
}

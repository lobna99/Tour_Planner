package com.example.tour_planner.model;

public class TourLog {
    private final String time;
    private final String comment;
    private final String name;
    private final int difficultly;
    private final String total_time;
    private final int rating;

    public TourLog(String time,String comment,int difficutly,String total_time,int rating,String name){
        this.time= time;
        this.comment= comment;
        this.difficultly = difficutly;
        this.total_time= total_time;
        this.rating= rating;
        this.name= name;
    }

    public String getComment() {
        return comment;
    }

    public int getDifficultly() {
        return difficultly;
    }

    public String getTotal_time() {
        return total_time;
    }

    public int getRating() {
        return rating;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}

package com.example.tour_planner.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourLog {
    private final StringProperty time = new SimpleStringProperty() ;
    private final StringProperty comment = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();;
    private final IntegerProperty difficultly = new SimpleIntegerProperty();;
    private final StringProperty total_time = new SimpleStringProperty();;
    private final IntegerProperty rating = new SimpleIntegerProperty();;

    public TourLog(String time,String comment,int difficutly,String total_time,int rating,String name){
        this.time.set(time);
        this.comment.set(comment);
        this.difficultly.set(difficutly);
        this.total_time.set(total_time);
        this.rating.set(rating);
        this.name.set(name);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty getTimeProperty(){
        return time;
    }
    public StringProperty getCommentProperty(){
        return comment;
    }

    public StringProperty getTotalTimeProperty(){
        return total_time;
    }

    public IntegerProperty getRatingProperty(){
        return rating;
    }

    public IntegerProperty getDifficutlyProperty(){
        return difficultly;
    }

    public int getDifficultly() {
        return difficultly.get();
    }

    public String getTotal_time() {
        return total_time.get();
    }

    public int getRating() {
        return rating.get();
    }

    public String getTime() {
        return time.get();
    }

    public String getName() {
        return name.get();
    }
}

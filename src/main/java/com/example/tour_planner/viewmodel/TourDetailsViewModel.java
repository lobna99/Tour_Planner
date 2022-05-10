package com.example.tour_planner.viewmodel;

import com.example.tour_planner.dal.DAL;
import com.example.tour_planner.model.Tour;
import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.Arrays;

public class TourDetailsViewModel {
    private Tour tour;
    private volatile boolean isInitValue = false;

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final IntegerProperty type = new SimpleIntegerProperty();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final StringProperty plannedTime = new SimpleStringProperty();
    private ObjectProperty<Image> map = new SimpleObjectProperty<>();

    public TourDetailsViewModel() {
        name.addListener((arg, oldVal, newVal) -> updateTourModel());
    }


    public String getName() {
        return name.get();
    }

    public ObjectProperty<Image> mapProperty() {
        return map;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty fromProperty() {
        return from;
    }

    public StringProperty toProperty() {
        return to;
    }

    public IntegerProperty typeProperty() {
        return type;
    }

    public double getDistance() {
        return distance.get();
    }

    public DoubleProperty distanceProperty() {
        return distance;
    }

    public String getPlannedTime() {
        return plannedTime.get();
    }

    public StringProperty plannedTimeProperty() {
        return plannedTime;
    }

    public void setTourModel(Tour mediaItemModel) {
        isInitValue = true;
        if (mediaItemModel == null) {
            // select the first in the list
            name.set("");
            plannedTime.set("00:00:00");
            distance.set(0.0);
            plannedTime.set("");
            return;
        }
        this.tour = mediaItemModel;
        name.setValue(mediaItemModel.getName());
        distance.set(mediaItemModel.getDistance());
        from.setValue(mediaItemModel.getFrom());
        to.setValue(mediaItemModel.getTo());
        type.setValue(mediaItemModel.getTransport_type());
        plannedTime.set(mediaItemModel.getDuration());
        map.set( new Image("file:src/main/resources/com/example/tour_planner/maps/" + mediaItemModel.getName()+ "_map.jpg"));
        isInitValue = false;
    }

    private void updateTourModel() {
        if (!isInitValue)
            DAL.getInstance().tourDao().update(tour, Arrays.asList(name.get(), distance.get(), plannedTime.get()));
    }


}

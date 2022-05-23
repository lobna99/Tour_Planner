package com.example.tour_planner.viewmodel;

import com.example.tour_planner.DAL.DAL;
import com.example.tour_planner.model.Tour;
import com.example.tour_planner.model.TourLog;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

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
    private final ObservableList<TourLog> observableLogs = FXCollections.observableArrayList();

    public ObservableList<TourLog> getObservableTourLogs() {
        return observableLogs;
    }
    public TourDetailsViewModel() {
        try {

            setTourLogs(DAL.getInstance().tourLogDao().getAll(name.get()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void setTourLogs(List<TourLog> tourLogs) {
        observableLogs.clear();
        observableLogs.addAll(tourLogs);
    }

    public void addLog(String comment, String totaltime, String diff, String rating) {
        TourLog tourLog = new TourLog("",comment,Integer.parseInt(diff),totaltime,Integer.parseInt(rating),name.get());
        try {
            DAL.getInstance().tourLogDao().create(tourLog);
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        observableLogs.add(tourLog);
    }
}

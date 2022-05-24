package com.example.tour_planner.viewmodel;

import com.example.tour_planner.DAL.DAL;
import com.example.tour_planner.model.Tour;
import com.example.tour_planner.model.TourLog;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TourDetailsViewModel {
    private Tour tour;
    private volatile boolean isInitValue = false;

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final StringProperty info = new SimpleStringProperty();
    private final IntegerProperty type = new SimpleIntegerProperty();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final StringProperty plannedTime = new SimpleStringProperty();
    private ObjectProperty<Image> map = new SimpleObjectProperty<>();


    public interface SelectionChangedListener {
        void changeSelection(TourLog TourLog);
    }

    private final List<SelectionChangedListener> listeners = new ArrayList<>();

    public void addSelectionChangedListener(SelectionChangedListener listener) {
        listeners.add(listener);
    }

    private final ObservableList<TourLog> observableLogs = FXCollections.observableArrayList();

    public ObservableList<TourLog> getObservableTourLogs() {
        return observableLogs;
    }

    public ChangeListener<TourLog> getChangeListener() {
        return (observableValue, oldValue, newValue) -> notifyListeners(newValue);
    }

    private void notifyListeners(TourLog newTour) {
        for (var listener : listeners) {
            listener.changeSelection(newTour);
        }
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
    public String getInfo() {
        return info.get();
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

    public StringProperty infoProperty() {
        return info;
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

    public void setTourModel(Tour TourModel) {
        isInitValue = true;
        if (TourModel == null) {
            // select the first in the list
            name.set("");
            plannedTime.set("00:00:00");
            distance.set(0.0);
            return;
        }
        this.tour = TourModel;
        name.setValue(TourModel.getName());
        distance.set(TourModel.getDistance());
        from.setValue(TourModel.getFrom());
        to.setValue(TourModel.getTo());
        type.setValue(TourModel.getTransport_type());
        plannedTime.set(TourModel.getDuration());
        info.setValue(TourModel.getContent());
        map.set( new Image("file:src/main/resources/com/example/tour_planner/maps/" + TourModel.getName()+ "_map.jpg"));
        isInitValue = false;
    }


    private void updateTourModel() {
        if (!isInitValue) {
            try {
                DAL.getInstance().tourDao().update(tour, Arrays.asList(name.get(),from.get(),to.get(),type.get(), distance.get(), plannedTime.get(),info.get()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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

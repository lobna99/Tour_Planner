package com.example.tour_planner.viewmodel;

import com.example.tour_planner.BL.BL;
import com.example.tour_planner.DAL.DAL;
import com.example.tour_planner.logger.ILoggerWrapper;
import com.example.tour_planner.logger.LoggerFactory;
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

public class TourDetailsViewModel {
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();
    private Tour tour;
    private volatile boolean isInitValue = false;

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty popularity = new SimpleStringProperty();
    private final StringProperty child = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final StringProperty info = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final StringProperty plannedTime = new SimpleStringProperty();
    private ObjectProperty<Image> map = new SimpleObjectProperty<>();

    public void updateLog(TourLog log, String comment, String totaltime, String diff, String rating) {
        try {
            DAL.getInstance().tourLogDao().update(log,Arrays.asList(comment,totaltime,diff,rating));
            setTourLogs(DAL.getInstance().tourLogDao().getAll(name.get()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public StringProperty popularityProperty() {
        return popularity;
    }

    public StringProperty childProperty() {
        return child;
    }

    public String getChild(){
        return child.get();
    }

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
            logger.fatal(e.toString());
        }
       // name.addListener((arg, oldVal, newVal) -> updateTourModel());
    }


    public String getPopularity(){
        return popularity.get();
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

    public StringProperty typeProperty() {
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
            name.setValue("");
            plannedTime.setValue("00:00:00");
            distance.setValue(0.0);
            child.setValue("0%");
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
        try {
            setTourLogs(DAL.getInstance().tourLogDao().getAll(name.get()));
            popularity.setValue(String.valueOf(BL.getInstance().getStatsCalculation().calculatePopularity(TourModel)));
            child.setValue(String.valueOf(BL.getInstance().getStatsCalculation().calculateChildF(TourModel))+"%");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        map.set( new Image("file:src/main/resources/com/example/tour_planner/maps/" + TourModel.getName()+ "_map.jpg"));
        isInitValue = false;
    }


    public void updateTourModel(String name, String descripText) {
        if (!isInitValue) {
            try {
                DAL.getInstance().tourDao().update(tour, Arrays.asList(name,descripText));
                popularity.setValue(String.valueOf(BL.getInstance().getStatsCalculation().calculatePopularity(tour)));
                child.setValue(String.valueOf(BL.getInstance().getStatsCalculation().calculateChildF(tour))+"%");
            } catch (SQLException e) {
                logger.fatal(e.toString());
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
            observableLogs.add(tourLog);
            popularity.setValue(String.valueOf(BL.getInstance().getStatsCalculation().calculatePopularity(tour)));
            child.setValue(String.valueOf(BL.getInstance().getStatsCalculation().calculateChildF(tour))+"%");
        } catch (SQLException | ParseException e) {
            logger.fatal(e.toString());
        }
    }
    public void removeLog(TourLog selectedItem) {
        try {
            DAL.getInstance().tourLogDao().delete(selectedItem);
        } catch (SQLException e) {
            logger.fatal(e.toString());
        }
        observableLogs.remove(selectedItem);
    }
}

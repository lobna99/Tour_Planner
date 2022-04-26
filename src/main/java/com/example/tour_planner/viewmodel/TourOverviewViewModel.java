package com.example.tour_planner.viewmodel;

import com.example.tour_planner.model.Tour;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TourOverviewViewModel {
    public interface SelectionChangedListener {
        void changeSelection(Tour mediaItem);
    }

    private List<SelectionChangedListener> listeners = new ArrayList<>();

    private ObservableList<Tour> observabletours = FXCollections.observableArrayList();

    /*public MediaOverviewViewModel()
    {
        setTours( DAL.getInstance().tourDao().getAll() );
    }*/

    public ObservableList<Tour> getObservableTours() {
        return observabletours;
    }

    public ChangeListener<Tour> getChangeListener() {
        return (observableValue, oldValue, newValue) -> notifyListeners(newValue);
    }

    public void addSelectionChangedListener(SelectionChangedListener listener) {
        listeners.add(listener);
    }

    public void removeSelectionChangedListener(SelectionChangedListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(Tour newTour) {
        for ( var listener : listeners ) {
            listener.changeSelection(newTour);
        }
    }

    public void setTours(List<Tour> tours) {
        observabletours.clear();
        observabletours.addAll(tours);
    }

    public void addNewTour() {
        //var tour = DALobject.getInstance().tourDao().create();
        //observabletours.add(tour);
    }

    public void deleteTour(Tour tour) {
        //DALobject.getInstance().tourDao().delete(tour);
        observabletours.remove(tour);
    }
}

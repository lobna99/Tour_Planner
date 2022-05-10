package com.example.tour_planner.viewmodel;

import com.example.tour_planner.dal.DAL;
import com.example.tour_planner.dal.http.HttpRequest;
import com.example.tour_planner.model.Tour;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.codehaus.jackson.JsonNode;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TourOverviewViewModel {
    public interface SelectionChangedListener {
        void changeSelection(Tour mediaItem);
    }

    private List<SelectionChangedListener> listeners = new ArrayList<>();

    private ObservableList<Tour> observabletours = FXCollections.observableArrayList();

    public TourOverviewViewModel()
    {
        try {
            setTours( DAL.getInstance().tourDao().getAll() );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
        for (var listener : listeners) {
            listener.changeSelection(newTour);
        }
    }

    public void setTours(List<Tour> tours) {
        observabletours.clear();
        observabletours.addAll(tours);
    }

    public void addNewTour(String From, String To, String text, LocalDate converter) throws JSONException, IOException, ParseException, SQLException {
        JsonNode obj;
        HttpRequest request = new HttpRequest();
            obj = request.getJsonnode(request.getResponse("https://www.mapquestapi.com/directions/v2/route?key=6Sl7sHB1l3EjHP83Jftbgz9uffLAlMXx&from=" + From + "&to=" + To + ""));
            request.saveImg("https://www.mapquestapi.com/staticmap/v5/map?key=6Sl7sHB1l3EjHP83Jftbgz9uffLAlMXx&size=650,650&defaultMarker=none&zoom=8&session=" + obj.get("route").get("sessionId").getTextValue(),text);
            Tour tour= new Tour(1,text,converter.toString(),From,To,obj.get("route").get("formattedTime").getTextValue(),obj.get("route").get("distance").getDoubleValue(),"");
            DAL.getInstance().tourDao().create(tour);
            observabletours.add(tour);
    }

    public void deleteTour(Tour tour) throws SQLException {
        DAL.getInstance().tourDao().delete(tour);
        observabletours.remove(tour);
    }
}

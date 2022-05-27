package com.example.tour_planner.viewmodel;

import com.example.tour_planner.DAL.DAL;
import com.example.tour_planner.DAL.api.HttpRequest;
import com.example.tour_planner.logger.ILoggerWrapper;
import com.example.tour_planner.logger.LoggerFactory;
import com.example.tour_planner.model.Tour;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.codehaus.jackson.JsonNode;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TourOverviewViewModel {
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    public interface SelectionChangedListener {
        void changeSelection(Tour mediaItem);
    }

    private final List<SelectionChangedListener> listeners = new ArrayList<>();

    private final ObservableList<Tour> observableTours = FXCollections.observableArrayList();

    public TourOverviewViewModel()
    {
        try {
            setTours( DAL.getInstance().tourDao().getAll("") );
        } catch (SQLException e) {
            logger.fatal(e.toString());
        }
    }

    public void generateTourReport(Tour selectedItem) throws IOException {
        DAL.getInstance().reportWriter().createTourReport(selectedItem);
    }

    public ObservableList<Tour> getObservableTours() {
        return observableTours;
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
        observableTours.clear();
        observableTours.addAll(tours);
    }

    public void addNewTour(String From, String To, String text, LocalDate converter, String descripText) throws JSONException, IOException, ParseException, SQLException {
            JsonNode obj;
            obj = HttpRequest.getJsonnode(HttpRequest.getResponse("https://www.mapquestapi.com/directions/v2/route?key=6Sl7sHB1l3EjHP83Jftbgz9uffLAlMXx&from=" + From + "&to=" + To + ""));
        assert obj != null;
        HttpRequest.saveImg("https://www.mapquestapi.com/staticmap/v5/map?key=6Sl7sHB1l3EjHP83Jftbgz9uffLAlMXx&size=650,650&defaultMarker=none&zoom=8&session=" + obj.get("route").get("sessionId").getTextValue(),text);
            Tour tour= new Tour(1,text,converter.toString(),From,To,obj.get("route").get("formattedTime").getTextValue(),obj.get("route").get("distance").getDoubleValue(),descripText);
            DAL.getInstance().tourDao().create(tour);
            observableTours.add(tour);
    }

    public void deleteTour(Tour tour) throws SQLException {
        DAL.getInstance().tourDao().delete(tour);
        observableTours.remove(tour);
    }
}

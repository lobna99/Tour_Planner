package com.example.tour_planner.DAL.fileServer;

import com.example.tour_planner.model.Tour;
import org.json.JSONException;

public interface IImportExport {

    public Tour importTour(String path);
    public void exportTour(Tour tour) throws JSONException;
}

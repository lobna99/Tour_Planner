package com.example.tour_planner.DAL.fileServer;

import com.example.tour_planner.model.Tour;

import java.io.IOException;
import java.util.List;

public interface IReportWriter {


    void createTourReport(Tour tour) throws IOException;


    public void createSummaryReport(String tourname, List<String> Data);
}

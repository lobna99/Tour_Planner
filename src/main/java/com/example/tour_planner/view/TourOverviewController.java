package com.example.tour_planner.view;


import com.example.tour_planner.model.Tour;
import com.example.tour_planner.viewmodel.TourOverviewViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;


public class TourOverviewController {
    @FXML
    public ListView<Tour> tourList;

    private final TourOverviewViewModel tourOverviewViewModel;

    public TourOverviewController(TourOverviewViewModel tourOverviewViewModel) {
        this.tourOverviewViewModel = tourOverviewViewModel;
    }

    public TourOverviewViewModel getTourOverviewViewModel() {
        return tourOverviewViewModel;
    }

    @FXML
    void initialize() {
        tourList.setItems(tourOverviewViewModel.getObservableTours());
        tourList.getSelectionModel().selectedItemProperty().addListener(tourOverviewViewModel.getChangeListener());
    }

    public void onButtonAdd(ActionEvent actionEvent) {
        FormsHandler.tourForm(tourOverviewViewModel);
        tourList.getSelectionModel().selectLast();
    }

    public void onButtonRemove(ActionEvent actionEvent) {
        try {
            if(tourList.getSelectionModel().getSelectedItem()!=null)
                tourOverviewViewModel.deleteTour(tourList.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onButtonTourReport(ActionEvent actionEvent) {
        try {
            if(tourList.getSelectionModel().getSelectedItem()!=null)
                tourOverviewViewModel.generateTourReport(tourList.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateSummarizeReport(ActionEvent actionEvent) {
    }
}

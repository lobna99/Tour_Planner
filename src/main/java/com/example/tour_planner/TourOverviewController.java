package com.example.tour_planner;


import com.example.tour_planner.model.Tour;
import com.example.tour_planner.viewmodel.TourOverviewViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;


public class TourOverviewController {
    @FXML
    public ListView<Tour> mediaItemList;

    private final TourOverviewViewModel mediaOverviewViewModel;

    public TourOverviewController(TourOverviewViewModel mediaOverviewViewModel) {
        this.mediaOverviewViewModel = mediaOverviewViewModel;
    }

    public TourOverviewViewModel getMediaOverviewViewModel() {
        return mediaOverviewViewModel;
    }

    @FXML
    void initialize() {
        mediaItemList.setItems(mediaOverviewViewModel.getObservableTours());
        mediaItemList.getSelectionModel().selectedItemProperty().addListener(mediaOverviewViewModel.getChangeListener());
    }

    public void onButtonAdd(ActionEvent actionEvent) {
        //mediaOverviewViewModel.addNewTour();
        mediaItemList.getSelectionModel().selectLast();
    }

    public void onButtonRemove(ActionEvent actionEvent) {
        //mediaOverviewViewModel.deleteTour(mediaItemList.getSelectionModel().getSelectedItem());
    }
}

package com.example.tour_planner.view;

import com.example.tour_planner.viewmodel.TourDetailsViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

import java.awt.*;
import java.text.NumberFormat;

public class TourDetailsController {

    @FXML
    public TextField nameDetail;
    public TextField fromDetail;
    public TextField toDetail;
    public TextField distanceDetail;
    public TextField timeDetail;
    public TextField infoDetail;
    public TextField typeDetail;
    public ImageView ivMap;

    private final TourDetailsViewModel tourDetailsViewModel;
    public TextArea description;
    public TableView LogTable;
    public TableColumn time;
    public TableColumn rating;
    public TableColumn total_time;
    public TableColumn comment;
    public TableColumn difficulty;


    public TourDetailsController(TourDetailsViewModel mediaDetailsViewModel) {
        this.tourDetailsViewModel = mediaDetailsViewModel;
    }

    public TourDetailsViewModel getTourDetailsViewModel() {
        return tourDetailsViewModel;
    }

    @FXML
    void initialize() {
        LogTable.setItems(tourDetailsViewModel.getObservableTourLogs());
        nameDetail.textProperty().bindBidirectional(tourDetailsViewModel.nameProperty());
        fromDetail.textProperty().bindBidirectional(tourDetailsViewModel.fromProperty());
        toDetail.textProperty().bindBidirectional(tourDetailsViewModel.toProperty());
        timeDetail.textProperty().bindBidirectional(tourDetailsViewModel.plannedTimeProperty());
        distanceDetail.textProperty().bindBidirectional(tourDetailsViewModel.distanceProperty(), NumberFormat.getInstance());
        typeDetail.textProperty().bindBidirectional(tourDetailsViewModel.typeProperty(),NumberFormat.getInstance());
        ivMap.imageProperty().bindBidirectional(tourDetailsViewModel.mapProperty());
    }

    public void onButtonAddLog(ActionEvent actionEvent) {
        FormsHandler.logForm(tourDetailsViewModel);
    }

    public void onButtonRemoveLog(ActionEvent actionEvent) {
    }
}

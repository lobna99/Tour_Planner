package com.example.tour_planner.view;

import com.example.tour_planner.model.TourLog;
import com.example.tour_planner.viewmodel.TourDetailsViewModel;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
    public Label popularity;
    public Label child_friendliness;
    @FXML
    private TableView<TourLog> LogTable;
    @FXML
    private TableColumn<TourLog,Number> nr;
    @FXML
    private TableColumn<TourLog,String> time;
    @FXML
    private TableColumn<TourLog,Number> rating;
    @FXML
    private TableColumn<TourLog,String> total_time;
    @FXML
    private TableColumn<TourLog,String> comment;
    @FXML
    private TableColumn<TourLog,Number> difficulty;


    public TourDetailsController(TourDetailsViewModel tourDetailsViewModel) {
        this.tourDetailsViewModel = tourDetailsViewModel;
    }

    public TourDetailsViewModel getTourDetailsViewModel() {
        return tourDetailsViewModel;
    }

    @FXML//        comment.pr

    void initialize() {
        popularity.textProperty().bindBidirectional(tourDetailsViewModel.popularityProperty());
        child_friendliness.textProperty().bindBidirectional(tourDetailsViewModel.childProperty());
        time.setCellValueFactory(data -> data.getValue().getTimeProperty());
        nr.setCellValueFactory(data -> data.getValue().idProperty());
        rating.setCellValueFactory(data -> data.getValue().getRatingProperty());
        total_time.setCellValueFactory(data -> data.getValue().getTotalTimeProperty());
        comment.setCellValueFactory(data -> data.getValue().getCommentProperty());
        difficulty.setCellValueFactory(data -> data.getValue().getDifficutlyProperty());
        LogTable.setItems(tourDetailsViewModel.getObservableTourLogs());
        nameDetail.textProperty().bindBidirectional(tourDetailsViewModel.nameProperty());
        fromDetail.textProperty().bindBidirectional(tourDetailsViewModel.fromProperty());
        toDetail.textProperty().bindBidirectional(tourDetailsViewModel.toProperty());
        infoDetail.textProperty().bindBidirectional(tourDetailsViewModel.infoProperty());
        timeDetail.textProperty().bindBidirectional(tourDetailsViewModel.plannedTimeProperty());
        distanceDetail.textProperty().bindBidirectional(tourDetailsViewModel.distanceProperty(), NumberFormat.getInstance());
        typeDetail.textProperty().bindBidirectional(tourDetailsViewModel.typeProperty());
        ivMap.imageProperty().bindBidirectional(tourDetailsViewModel.mapProperty());
    }

    public void onButtonAddLog(ActionEvent actionEvent) {
        FormsHandler.logForm(tourDetailsViewModel);
    }

    public void onButtonRemoveLog(ActionEvent actionEvent) {
        if (LogTable.getSelectionModel().getSelectedItem()!=null)
            tourDetailsViewModel.removeLog(LogTable.getSelectionModel().getSelectedItem());
    }

    public void onButtonUpdateTour(ActionEvent actionEvent) {
        FormsHandler.tourUpdateForm(tourDetailsViewModel);
    }

    public void onButtonUpdateLog(ActionEvent actionEvent) {
        if (LogTable.getSelectionModel().getSelectedItem()!=null)
            FormsHandler.tourLogUpdateForm(tourDetailsViewModel, LogTable.getSelectionModel().getSelectedItem());
    }
}

package com.example.tour_planner.view;


import com.example.tour_planner.dal.http.HttpRequest;
import com.example.tour_planner.model.Tour;
import com.example.tour_planner.viewmodel.TourOverviewViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.codehaus.jackson.JsonNode;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;


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
        showForm();
        tourList.getSelectionModel().selectLast();
    }

    public void onButtonRemove(ActionEvent actionEvent) {
        try {
            tourOverviewViewModel.deleteTour(tourList.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showForm() {
        Stage stage = new Stage();
        VBox box = new VBox();
        box.setPadding(new Insets(10));

        // How to center align content in a layout manager in JavaFX
        box.setAlignment(Pos.CENTER);

        Label label = new Label("Create a Tour");

        TextField Name = new TextField();
        Name.setPromptText("enter tour name");
        TextField From = new TextField();
        From.setPromptText("enter from");
        TextField To = new TextField();
        To.setPromptText("enter to");
        Label labeld = new Label("Choose Date");
        DatePicker Date = new DatePicker();

        Button btnSubmit = new Button();
        btnSubmit.setText("submit");

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JsonNode obj;
                HttpRequest request = new HttpRequest();
                try {
                    //validate Userinput
                    if(!From.getText().matches("[a-zA-z]*")
                    || !To.getText().matches("[a-zA-z]*")
                    || !To.getText().matches("[a-zA-z]*")
                    || !Date.toString().matches("[0-9]{2}.[0-9]{2}.[0-9]{4}")) {
                        //TODO: ALERT SOME ERROR MESSAGE

                    }
                    else tourOverviewViewModel.addNewTour(From.getText(),To.getText(),Name.getText(),Date.getValue());
                } catch (JSONException | IOException | SQLException | ParseException e) {
                    e.printStackTrace();
                }
                stage.close(); // return to main window
            }
        });
        box.getChildren().add(label);
        box.getChildren().add(Name);
        box.getChildren().add(From);
        box.getChildren().add(To);
        box.getChildren().add(labeld);
        box.getChildren().add(Date);
        box.getChildren().add(btnSubmit);
        Scene scene = new Scene(box, 350, 250);
        stage.setScene(scene);
        stage.show();

    }
}

package com.example.tour_planner.view;

import com.example.tour_planner.BL.BL;
import com.example.tour_planner.logger.ILoggerWrapper;
import com.example.tour_planner.logger.LoggerFactory;
import com.example.tour_planner.model.TourLog;
import com.example.tour_planner.viewmodel.TourDetailsViewModel;
import com.example.tour_planner.viewmodel.TourOverviewViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class FormsHandler {

    private static final ILoggerWrapper logger = LoggerFactory.getLogger();
    private static Alert alert = new Alert(Alert.AlertType.NONE);

    public static void tourForm(TourOverviewViewModel tourOverviewViewModel) {
        Stage stage = new Stage();
        VBox box = new VBox();
        box.setPadding(new Insets(10));

        // How to center align content in a layout manager in JavaFX
        box.setAlignment(Pos.CENTER);

        Label label = new Label("Create a Tour");

        TextField Name = new TextField();
        Name.setId("name");
        Name.setPromptText("enter tour name");
        TextField From = new TextField();
        From.setPromptText("enter from");
        TextField To = new TextField();
        To.setPromptText("enter to");
        ChoiceBox<String> transport = new ChoiceBox<>();
        transport.setId("choice");
        transport.getItems().add("CAR");
        transport.getItems().add("BICYCLE");
        transport.getItems().add("PEDESTRIAN");
        TextField Descrip = new TextField();
        Descrip.setPromptText("Description");
        Button btnSubmit = new Button();
        btnSubmit.setText("submit");

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (!From.getText().matches("[a-zA-ZäüöÄÜÖ]*")
                            || !Name.getText().matches("[a-zA-ZäüöÄÜÖ0-9]*")
                            || BL.getInstance().getSearchLogic().nameExists(Name.getText())
                            || !To.getText().matches("[a-zA-z-ZäüöÄÜÖ]*")
                            ) {
                        logger.warn("Input is not in right format");
                        alert.setAlertType(Alert.AlertType.WARNING);
                        alert.setContentText("Input is not in right format");
                        alert.show();
                        return;
                    }
                    tourOverviewViewModel.addNewTour(transport.getValue(),From.getText(), To.getText(),Name.getText(), Descrip.getText());
                } catch (JSONException | IOException | SQLException | ParseException e) {
                    logger.error(e.toString());
                }
                stage.close(); // return to main window
            }
        });
        box.getChildren().addAll(label, Name, From, To,transport,Descrip, btnSubmit);
        Scene scene = new Scene(box, 350, 250);
        stage.setScene(scene);
        stage.setTitle("Add Tour");
        stage.show();
    }

    public static void logForm(TourDetailsViewModel tourDetailsViewModel) {
        Stage stage = new Stage();
        GridPane box = new GridPane();
        box.setVgap(10);
        box.setHgap(10);
        box.setPadding(new Insets(25, 25, 25, 25));

        // How to center align content in a layout manager in JavaFX
        box.setAlignment(Pos.CENTER_LEFT);

        Label label = new Label("Create log");

        TextField comment = new TextField();
        comment.setPromptText("Comment");
        TextField totaltime = new TextField();
        totaltime.setPromptText("Total time spent");
        Label diff = new Label("Difficulty");
        ToggleGroup group = new ToggleGroup();
        RadioButton a = new RadioButton("1");
        a.setId("diff1");
        a.setToggleGroup(group);
        a.setSelected(true);
        RadioButton b = new RadioButton("2");
        b.setToggleGroup(group);
        RadioButton c = new RadioButton("3");
        c.setToggleGroup(group);
        RadioButton d = new RadioButton("4");
        d.setToggleGroup(group);
        Label rating = new Label("Rating");
        ToggleGroup groupR = new ToggleGroup();
        RadioButton aR = new RadioButton("1");
        aR.setToggleGroup(groupR);
        aR.setSelected(true);
        RadioButton bR = new RadioButton("2");
        bR.setToggleGroup(groupR);
        RadioButton cR = new RadioButton("3");
        cR.setToggleGroup(groupR);
        RadioButton dR = new RadioButton("4");
        dR.setToggleGroup(groupR);
        RadioButton eR = new RadioButton("5");
        eR.setToggleGroup(groupR);


        Button btnSubmit = new Button();
        btnSubmit.setText("submit");

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!totaltime.getText().matches("[0-9]*")) {
                    logger.warn("Total Time Spent can only be a numeric value");
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("Total Time Spent can only be a numeric value");
                    alert.show();
                    return;
                }
                tourDetailsViewModel.addLog(comment.getText(), totaltime.getText(),((RadioButton) a.getToggleGroup().getSelectedToggle()).getText(), ((RadioButton) aR.getToggleGroup().getSelectedToggle()).getText());
                stage.close(); // return to main window
            }
        });
        box.add(label, 0, 0, 2, 1);
        box.add(comment, 1, 1);
        box.add(totaltime, 1, 2);
        box.add(diff, 0, 3, 2, 1);
        box.add(a, 0, 4, 1, 1);
        box.add(b, 1, 4, 1, 1);
        box.add(c, 2, 4, 1, 1);
        box.add(d, 3, 4, 1, 1);
        box.add(rating, 0, 5, 2, 1);
        box.add(aR, 0, 6);
        box.add(bR, 1, 6);
        box.add(cR, 2, 6);
        box.add(dR, 3, 6);
        box.add(eR, 4, 6);
        box.add(btnSubmit, 1, 7);

        //box.getChildren().addAll(label,comment,totaltime,diff,a,b,c,d,aR,bR,cR,dR,eR,btnSubmit);
        Scene scene = new Scene(box, 350, 250);
        stage.setScene(scene);
        stage.setTitle("Update Tourlog");
        stage.show();
    }

    public static void tourUpdateForm(TourDetailsViewModel tourDetailsViewModel) {
        Stage stage = new Stage();
        VBox box = new VBox();
        box.setPadding(new Insets(10));

        // How to center align content in a layout manager in JavaFX
        box.setAlignment(Pos.CENTER);

        Label label = new Label("Update Tour");

        TextField Name = new TextField();
        Name.setPromptText("update tour name");
        TextField From = new TextField();
        From.setPromptText("update from");
        TextField To = new TextField();
        To.setPromptText("update to");
        TextField Descrip = new TextField();
        Descrip.setPromptText("update description");

        Button btnUpdate = new Button();
        btnUpdate.setId("update");
        btnUpdate.setText("update");

        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                                    if (!From.getText().matches("[a-zA-z]*")
                            || !To.getText().matches("[a-zA-z]*")
                            || !Name.getText().matches("[a-zA-z]*")
                            ) {
                        logger.warn("Input is not in right format");
                        alert.setAlertType(Alert.AlertType.WARNING);
                        alert.setContentText("Input is not in right format");
                        alert.show();
                        return;
                    }
                tourDetailsViewModel.updateTourModel(Name.getText(),Descrip.getText());
                stage.close(); // return to main window
            }
        });
        box.getChildren().addAll(label, Name, Descrip, btnUpdate);
        Scene scene = new Scene(box, 350, 250);
        stage.setScene(scene);
        stage.setTitle("Update Tour");
        stage.show();
    }

    public static void tourLogUpdateForm(TourDetailsViewModel tourDetailsViewModel, TourLog selectedItem) {
        Stage stage = new Stage();
        GridPane box = new GridPane();
        box.setVgap(10);
        box.setHgap(10);
        box.setPadding(new Insets(25, 25, 25, 25));

        // How to center align content in a layout manager in JavaFX
        box.setAlignment(Pos.CENTER_LEFT);

        Label label = new Label("Update log");

        TextField comment = new TextField();
        comment.setId("comment");
        comment.setPromptText("Comment");
        TextField totaltime = new TextField();
        totaltime.setId("total_time");
        totaltime.setPromptText("Total time spent");
        Label diff = new Label("Difficulty");
        ToggleGroup group = new ToggleGroup();
        RadioButton a = new RadioButton("1");
        a.setToggleGroup(group);
        a.setSelected(true);
        RadioButton b = new RadioButton("2");
        b.setId("diff2");
        b.setToggleGroup(group);
        RadioButton c = new RadioButton("3");
        c.setToggleGroup(group);
        RadioButton d = new RadioButton("4");
        d.setToggleGroup(group);
        Label rating = new Label("Rating");
        ToggleGroup groupR = new ToggleGroup();
        RadioButton aR = new RadioButton("1");
        aR.setToggleGroup(groupR);
        aR.setSelected(true);
        RadioButton bR = new RadioButton("2");
        bR.setToggleGroup(groupR);
        RadioButton cR = new RadioButton("3");
        cR.setToggleGroup(groupR);
        RadioButton dR = new RadioButton("4");
        dR.setToggleGroup(groupR);
        RadioButton eR = new RadioButton("5");
        eR.setToggleGroup(groupR);


        Button btnSubmit = new Button();
        btnSubmit.setText("submit");

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!totaltime.getText().matches("[0-9]*")) {
                    logger.warn("Total Time Spent can only be a numeric value");
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("Total Time Spent can only be a numeric value");
                    alert.show();
                    return;
                }
                tourDetailsViewModel.updateLog(selectedItem,comment.getText(), totaltime.getText(),((RadioButton) a.getToggleGroup().getSelectedToggle()).getText(), ((RadioButton) aR.getToggleGroup().getSelectedToggle()).getText());
                stage.close(); // return to main window
            }
        });
        box.add(label, 0, 0, 2, 1);
        box.add(comment, 1, 1);
        box.add(totaltime, 1, 2);
        box.add(diff, 0, 3, 2, 1);
        box.add(a, 0, 4, 1, 1);
        box.add(b, 1, 4, 1, 1);
        box.add(c, 2, 4, 1, 1);
        box.add(d, 3, 4, 1, 1);
        box.add(rating, 0, 5, 2, 1);
        box.add(aR, 0, 6);
        box.add(bR, 1, 6);
        box.add(cR, 2, 6);
        box.add(dR, 3, 6);
        box.add(eR, 4, 6);
        box.add(btnSubmit, 1, 7);

        //box.getChildren().addAll(label,comment,totaltime,diff,a,b,c,d,aR,bR,cR,dR,eR,btnSubmit);
        Scene scene = new Scene(box, 350, 250);
        stage.setScene(scene);
        stage.setTitle("Update Tourlog");
        stage.show();
    }

}

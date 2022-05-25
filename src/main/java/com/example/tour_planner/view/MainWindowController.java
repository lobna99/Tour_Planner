package com.example.tour_planner.view;


import com.example.tour_planner.viewmodel.MainWindowViewModel;
import com.example.tour_planner.viewmodel.TourOverviewViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class MainWindowController {
    public VBox vbox;
    // Controllers of included fxml-files are injected here
    // fx:id Attribute of <fx:include> tag + "Controller"
    // tutorial see https://riptutorial.com/javafx/example/7285/nested-controllers
    @FXML private SearchBarController searchBarController;    // injected controller of SearchBar.fxml
    @FXML private TourOverviewViewModel mediaOverviewController;    // injected controller of MediaOverview.fxml
    @FXML private TourDetailsController mediaDetailsController;    // injected controller of MediaDetails.fxml

    private final MainWindowViewModel mainViewModel;

    public MainWindowController(MainWindowViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    public MainWindowViewModel getMainViewModel() {
        return mainViewModel;
    }

    @FXML void initialize() {
    }

    public void onMenuFileQuitClicked(ActionEvent actionEvent) {
        Platform.exit();
    }
    public void onMenuHelpAboutClicked(ActionEvent actionEvent) {
        Alert aboutBox = new Alert(Alert.AlertType.INFORMATION, "Semesterproject BIF4-SWE2\nby Lobna Boutahar and Ina Appeltauer");
        aboutBox.setTitle("About TourPlanner");
        aboutBox.showAndWait();
    }

    public void importTour(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wähle die Datei");
// damit kommen wir direkt in den Benutzerordner auf dem Rechner des Nutzers
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

// damit legen wir fest, welche Datentypen wir zulassen
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON", "*.json*")
        );

        fileChooser.setTitle("Wähle die Datei");
        File file = fileChooser.showOpenDialog(vbox.getScene().getWindow());
        if (file != null){
           mainViewModel.addImportTour(file.getPath());
        }
    }
}

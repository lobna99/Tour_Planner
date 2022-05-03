package com.example.tour_planner;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class TourApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLDependencyInjection.load("MainWindow.fxml", Locale.GERMAN);  // Locale.GERMANY, Locale.ENGLISH
        Scene scene = new Scene(root);


        primaryStage.setScene(scene);

        //scene.getStylesheets().add(getClass().getClassLoader().getResource("images\\stylesheet.css"));
        primaryStage.setTitle("Tour planner");
        //scene.getStylesheets().add("/com/example/tour_planner/css/main.css");
        //scene.getStylesheets().add( getClass().getResource( "C:\\Users\\inaap\\Dokumente\\GitHub\\src\\main\\resources\\css\\main.css" ).toExternalForm() );
        primaryStage.show();
    }
}

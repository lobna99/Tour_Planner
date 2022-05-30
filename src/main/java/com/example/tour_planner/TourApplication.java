package com.example.tour_planner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class TourApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    //private Locale chosenLang;

    @Override
    public void start(Stage primaryStage) throws IOException {

        final Locale[] chosenLang = new Locale[1];
        Stage stage = new Stage();
        GridPane box = new GridPane();
        box.setVgap(10);
        box.setHgap(10);
        box.setPadding(new Insets(25, 25, 25, 25));

        // How to center align content in a layout manager in JavaFX
        box.setAlignment(Pos.CENTER_LEFT);
        box.setStyle("-fx-background-color: #3f054f; -fx-border-color: #66e0ff");

        Label label = new Label("Select language");
        label.setStyle("-fx-text-fill: white;");
        ToggleGroup group = new ToggleGroup();
        RadioButton a = new RadioButton("1");
        a.setToggleGroup(group);
        a.setSelected(true);
        a.setText("German");
        a.setStyle("-fx-text-fill: white");
        RadioButton b = new RadioButton("2");
        b.setToggleGroup(group);
        b.setText("English");
        b.setStyle("-fx-text-fill: white;");

        Button btnSubmit = new Button();
        btnSubmit.setText("submit");
        btnSubmit.setStyle("-fx-text-fill: white; -fx-border-color: #fa1bf2; -fx-background-color: #22023b");

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Objects.equals(((RadioButton) a.getToggleGroup().getSelectedToggle()).getText(), "German"))
                    chosenLang[0] = Locale.GERMAN;
                else if (Objects.equals(((RadioButton) a.getToggleGroup().getSelectedToggle()).getText(), "English"))
                    chosenLang[0] = Locale.ENGLISH;
                try {
                    loadApp(chosenLang[0],primaryStage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.close();
            }
        });
        box.add(label,0,0);
        box.add(a, 1, 1);
        box.add(b, 1, 2);
        box.add(btnSubmit, 1, 3);
        Scene scene1 = new Scene(box, 350, 250);
        stage.setScene(scene1);
        stage.setTitle("Select language");
        stage.show();

    }

    public void loadApp(Locale locale,Stage primaryStage) throws IOException {
        Parent root = FXMLDependencyInjection.load("MainWindow.fxml", locale);  // Locale.GERMANY, Locale.ENGLISH
        Scene scene = new Scene(root, 1000, 550);


        primaryStage.setScene(scene);

        //scene.getStylesheets().add(getClass().getClassLoader().getResource("images\\stylesheet.css"));
        primaryStage.setTitle("Tour planner");
        //scene.getStylesheets().add("/com/example/tour_planner/css/main.css");
        //scene.getStylesheets().add( getClass().getResource( "C:\\Users\\inaap\\Dokumente\\GitHub\\src\\main\\resources\\css\\main.css" ).toExternalForm() );
        primaryStage.show();
    }
}

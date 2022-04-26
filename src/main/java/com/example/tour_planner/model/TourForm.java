package com.example.tour_planner.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TourForm {

    public void showForm() throws JSONException, IOException {
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
        DatePicker Date= new DatePicker();

        Button btnSubmit = new Button();
        btnSubmit.setText("submit");

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    getResponse(From.getText(),To.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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


    public void getResponse(String from, String to) throws IOException, JSONException {

            String url = "https://www.mapquestapi.com/directions/v2/route?key=6Sl7sHB1l3EjHP83Jftbgz9uffLAlMXx&from="+from+"&to="+to+"";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print in String
            System.out.println(response.toString());

            //Read JSON response and print
            JSONObject myResponse = new JSONObject(response.toString());
            System.out.println("result after Reading JSON Response");
            System.out.println("origin- "+myResponse.getString("origin"));

        }

}

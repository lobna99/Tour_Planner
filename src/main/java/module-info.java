module com.example.tour_planner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires json;


    opens com.example.tour_planner to javafx.fxml;
    exports com.example.tour_planner;
    exports com.example.tour_planner.view;
    opens com.example.tour_planner.view to javafx.fxml;
}
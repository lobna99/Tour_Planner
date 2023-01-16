module com.example.tour_planner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires json;
    requires jackson.core.asl;
    requires jackson.mapper.asl;
    requires java.desktop;
    requires org.apache.logging.log4j;
    requires io;
    requires kernel;
    requires layout;
    requires json.simple;


    opens com.example.tour_planner to javafx.fxml;
    exports com.example.tour_planner;
    exports com.example.tour_planner.view;
    opens com.example.tour_planner.view to javafx.fxml;
}
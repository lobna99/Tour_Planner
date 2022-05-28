package com.example.tour_planner.DAL.DAOs;


import com.example.tour_planner.DAL.db.DBconnection;
import com.example.tour_planner.logger.ILoggerWrapper;
import com.example.tour_planner.logger.LoggerFactory;
import com.example.tour_planner.model.Tour;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TourDao implements Dao<Tour> {

    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    @Override
    public Optional<Tour> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<Tour> getAll(String name) throws SQLException {
        ArrayList<Tour> tours = new ArrayList<Tour>();
        PreparedStatement statement = DBconnection.getConnection().prepareStatement("""
                    SELECT *
                    FROM tour
                """);
        ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                tours.add(new Tour(rs.getString("Transport_type"), rs.getString("Name"), rs.getString("From"), rs.getString("To"),rs.getString("Duration"), rs.getDouble("Distance"), rs.getString("route_info")));
            }
        rs.close();
        statement.close();
        return tours;
    }

    @Override
    public void create(Tour tour) throws SQLException, ParseException {

        PreparedStatement statement = null;
        statement = DBconnection.getConnection().prepareStatement("""
                 INSERT INTO tour ("Duration", "Distance", "Name", "From", "To", "Transport_type", route_info)
                 VALUES(?,?,?,?,?,?,?)
                """);

        statement.setString(1, tour.getDuration());
        statement.setDouble(2, tour.getDistance());
        statement.setString(3, tour.getName());
        statement.setString(4, tour.getFrom());
        statement.setString(5, tour.getTo());
        statement.setString(6, tour.getTransport_type());
        statement.setString(7, tour.getContent());
        statement.execute();
        statement.close();
    }

    @Override
    public void update(Tour tour, List<?> params) throws SQLException {
        PreparedStatement statement = DBconnection.getConnection().prepareStatement("""
                         UPDATE tour 
                         SET "Name"=?,route_info=?
                         WHERE "Name"=?
                         """);
        statement.setString(1, String.valueOf(params.get(0)));
        statement.setString(2, String.valueOf(params.get(1)));
        statement.setString(3, tour.getName());
        statement.executeUpdate();
        statement.close();
        File file = new File("src/main/resources/com/example/tour_planner/maps/"+tour.getName()+"_map.jpg");

// File (or directory) with new name
        File file2 = new File("src/main/resources/com/example/tour_planner/maps/"+params.get(0).toString()+"_map.jpg");

        if (file2.exists())
            try {
                throw new java.io.IOException("file exists");
            } catch (IOException e) {
                logger.error(e.toString());
            }

// Rename file (or directory)
        boolean success = file.renameTo(file2);
        if (!success) {
            logger.error("File was not renamed");
        }
    }

    @Override
    public void delete(Tour tour) throws SQLException {
        PreparedStatement statement = DBconnection.getConnection().prepareStatement("""
                         DELETE
                         FROM tour
                         WHERE "Name"=?;
                         """);
        statement.setString(1, tour.getName());
        statement.execute();
        statement.close();
        File file = new File("src/main/resources/com/example/tour_planner/maps/"+tour.getName()+"_map.jpg");
        if (file.delete()) logger.debug("Map deleted");
        else logger.error("failed to delete map");
    }

}

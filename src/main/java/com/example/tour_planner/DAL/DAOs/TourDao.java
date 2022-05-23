package com.example.tour_planner.DAL.DAOs;


import com.example.tour_planner.DAL.db.DBconnection;
import com.example.tour_planner.model.Tour;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TourDao implements Dao<Tour> {
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
                tours.add(new Tour(rs.getInt("Transport_type"), rs.getString("Name"), rs.getString("Date"), rs.getString("From"), rs.getString("To"), rs.getString("Duration"), rs.getDouble("Distance"), rs.getString("route_info")));
            }
        rs.close();
        statement.close();
        return tours;
    }

    @Override
    public void create(Tour tour) throws SQLException, ParseException {

        PreparedStatement statement = null;
        statement = DBconnection.getConnection().prepareStatement("""
                 INSERT INTO tour ("Date", "Duration", "Distance", "Name", "From", "To", "Transport_type", route_info)
                 VALUES(?,?,?,?,?,?,?,?)
                """);

        statement.setString(1, tour.getDate());
        statement.setString(2, tour.getDuration());
        statement.setDouble(3, tour.getDistance());
        statement.setString(4, tour.getName());
        statement.setString(5, tour.getFrom());
        statement.setString(6, tour.getTo());
        statement.setInt(7, tour.getTransport_type());
        statement.setString(8, tour.getContent());
        statement.execute();
        statement.close();
    }

    @Override
    public void update(Tour tour, List<?> params) {

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
    }

}

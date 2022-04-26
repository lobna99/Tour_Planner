package com.example.tour_planner.dal;


import com.example.tour_planner.db.DBconnection;
import com.example.tour_planner.db.getDBConnection;
import com.example.tour_planner.model.Tour;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TourDao implements Dao<Tour> ,getDBConnection{
    @Override
    public Optional<Tour> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<Tour> getAll() {
        return null;
    }

    @Override
    public void create(Tour tour) {

        PreparedStatement statement = null;
        try {
            statement = Connection.getConnection().prepareStatement("""
                     INSERT INTO tour ("Date", "Duration", "Distance", "Name", "From", "To", "Transport_type", estimated_time, route_info)
                     VALUES(?,?,?,?,?,?,?,?,?)
                    """);

          /*  statement.setString(1,tour.getDate());
            statement.setTime(2,tour.getDuration());
            statement.setDouble(3,tour.getDistance());
            statement.setString(4,tour.getName());
            statement.setString(5,tour.getFrom());
            statement.setString(6,tour.getTo());
            statement.setInt(7,tour.getTransport_type());*/
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

        @Override
    public void update(Tour tour, List<?> params) {

    }

    @Override
    public void delete(Tour tour) {

    }
}

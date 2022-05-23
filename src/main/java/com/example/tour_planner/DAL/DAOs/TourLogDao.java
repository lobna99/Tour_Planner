package com.example.tour_planner.DAL.DAOs;

import com.example.tour_planner.DAL.db.DBconnection;
import com.example.tour_planner.model.Tour;
import com.example.tour_planner.model.TourLog;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TourLogDao implements Dao<TourLog> {
    @Override
    public Optional<TourLog> get(int id) {
        return Optional.empty();
    }


    @Override
    public List<TourLog> getAll(String name) throws SQLException {
        ArrayList<TourLog> tourLogs = new ArrayList<TourLog>();
        PreparedStatement statement = DBconnection.getConnection().prepareStatement("""
                    SELECT *
                    FROM tour_log
                    WHERE tour = ?
                """);
        statement.setString(1,name);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            tourLogs.add(new TourLog(rs.getTimestamp("time").toString(),rs.getString("comment"),rs.getInt("difficutly"),rs.getString("total time"),rs.getInt("rating"),rs.getString("tour")));
        }
        rs.close();
        statement.close();
        return tourLogs;
    }

    @Override
    public void create(TourLog tr) throws SQLException, ParseException {

        PreparedStatement statement = DBconnection.getConnection().prepareStatement("""
                 INSERT INTO tour_log (comment, difficulty, "total time", rating, tour)
                 VALUES(?,?,?,?,?)
                """);

        statement.setString(1, tr.getComment());
        statement.setDouble(2, tr.getDifficultly());
        statement.setString(3, tr.getTotal_time());
        statement.setInt(4, tr.getRating());
        statement.setString(5, tr.getName());
        statement.execute();
        statement.close();
    }

    @Override
    public void update(TourLog tourLog, List<?> params) {

    }

    @Override
    public void delete(TourLog tourLog) throws SQLException {

    }
}

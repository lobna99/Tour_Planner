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
        String query ="";
        String sql1 = """
                    SELECT *
                    FROM tour_log
                    WHERE tour = ?
                """;
        String sql2 ="""
                    SELECT *
                    FROM tour_log""";

        if (name!=null) query = name.equals("") ? sql2 : sql1;
        PreparedStatement statement = DBconnection.getConnection().prepareStatement(query);
        if (name!=null) if(!name.equals("")) statement.setString(1, name);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            TourLog getT = new TourLog(rs.getTimestamp("time").toString(),rs.getString("comment"),rs.getInt("difficulty"),rs.getString("total time"),rs.getInt("rating"),rs.getString("tour"));
            getT.setId(rs.getInt("id"));
            tourLogs.add(getT);
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
                 RETURNING id,time
                """);

        statement.setString(1, tr.getComment());
        statement.setDouble(2, tr.getDifficultly());
        statement.setString(3, tr.getTotal_time());
        statement.setInt(4, tr.getRating());
        statement.setString(5, tr.getName());
        ResultSet set = statement.executeQuery();
        set.next();
        tr.setId(set.getInt(1));
        tr.setTime(set.getTimestamp(2).toString());
        statement.close();
    }

    @Override
    public void update(TourLog tourLog, List<?> params) throws SQLException {
        PreparedStatement statement = DBconnection.getConnection().prepareStatement("""
                UPDATE tour_log
                SET comment = ?,"total time" = ?,rating = ?,difficulty = ?
                WHERE id = ?
                """);
        statement.setString(1, String.valueOf(params.get(0)));
        statement.setString(2, String.valueOf(params.get(1)));
        statement.setInt(3, Integer.parseInt(params.get(2).toString()));
        statement.setInt(4, Integer.parseInt(params.get(3).toString()));
        statement.setInt(5, tourLog.getId());
        statement.executeUpdate();
        statement.close();

    }

    @Override
    public void delete(TourLog tourLog) throws SQLException {
        PreparedStatement statement = DBconnection.getConnection().prepareStatement("""
                         DELETE
                         FROM tour_log
                         WHERE id=?;
                         """);
        statement.setInt(1,tourLog.getId());
        statement.execute();
        statement.close();
    }
}

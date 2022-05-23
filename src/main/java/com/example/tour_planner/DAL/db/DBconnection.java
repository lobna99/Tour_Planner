
package com.example.tour_planner.DAL.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection  {//Singelton Pattern only one object of this class
    private static DBconnection OBJ =null;
    private static final String DBurl ="jdbc:postgresql://localhost:5432/tourplanner";
    private static final String DBuser="swe1user";
    private static final String DBpass="swe1pw";

    private DBconnection(){
    }
    public static DBconnection getInstance() {
        if (OBJ == null)
            OBJ = new DBconnection();

        return OBJ;
    }
    public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(DBurl,DBuser,DBpass);
    }
}


package com.example.tour_planner.DAL.db;
import com.example.tour_planner.logger.ILoggerWrapper;
import com.example.tour_planner.logger.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBconnection  {
    //Singelton Pattern only one object of this class
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();
    private static DBconnection OBJ =null;
    private static  String DBurl;
    private static  String DBuser;
    private static  String DBpass;

    private DBconnection(){
    }
    public static DBconnection getInstance() {
        if (OBJ == null)
            OBJ = new DBconnection();

        return OBJ;
    }
    public static Connection getConnection() throws SQLException {
        Properties appProps = new Properties();
        try {
            appProps.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("database.properties"));
            DBurl = appProps.getProperty("database_url");
            DBuser = appProps.getProperty("database_user");
            DBpass = appProps.getProperty("database_password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(DBurl,DBuser,DBpass);
    }
}

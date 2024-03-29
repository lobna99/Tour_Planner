package com.example.tour_planner.DAL;

import com.example.tour_planner.DAL.DAOs.Dao;
import com.example.tour_planner.DAL.DAOs.TourDao;
import com.example.tour_planner.DAL.DAOs.TourLogDao;
import com.example.tour_planner.DAL.fileServer.ImportExport;
import com.example.tour_planner.DAL.fileServer.ReportWriter;
import com.example.tour_planner.model.Tour;
import com.example.tour_planner.model.TourLog;

public class DAL {
    private Dao<Tour> tourDao;
    private Dao<TourLog> tourLogDao;
    private ReportWriter reportWriter;
    private ImportExport fileAccess;

    private DAL(){
        tourLogDao = new TourLogDao();
        fileAccess = new ImportExport();
        tourDao= new TourDao();
        reportWriter = new ReportWriter();
    }

    public Dao<Tour> tourDao() {
        return tourDao;
    }
    public Dao<TourLog> tourLogDao() {
        return tourLogDao;
    }
    public ReportWriter reportWriter() {
        return reportWriter;
    }
    public ImportExport fileAccess() {
        return fileAccess;
    }

    private static final DAL instance = new DAL();

    public static DAL getInstance() {
        return instance;
    }
}

package com.example.tour_planner.dal;

import com.example.tour_planner.model.Tour;

import java.util.ArrayList;

public class DAL {
    private Dao<Tour> tourDao;

    private DAL(){
        tourDao= new TourDao();
    }

    public Dao<Tour> tourDao() {
        return tourDao;
    }

    private static final DAL instance = new DAL();

    public static DAL getInstance() {
        return instance;
    }
}

package com.example.tour_planner.BL;

import com.example.tour_planner.DAL.DAL;
import com.example.tour_planner.model.Tour;
import com.example.tour_planner.model.TourLog;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatsCalculation {

    private DAL dal;

    public StatsCalculation(){
        this(DAL.getInstance());
    }
    //for tests
    StatsCalculation(DAL dal){
        this.dal=dal;
    }
    public void averageStats(Tour t) throws SQLException, IOException {
        List<TourLog> tourLogs = dal.tourLogDao().getAll(t.getName());
        List<String> averages = new ArrayList<>();
        double sumRating = 0;
        double sumDifficulty = 0;
        double sumTotaltime = 0;

        for (TourLog x : tourLogs) {
            sumRating += x.getRating();
            sumDifficulty += x.getDifficultly();
            sumTotaltime += Integer.parseInt(x.getTotal_time());
        }
        double avgR =sumRating/tourLogs.size();
        double avgD =sumDifficulty/tourLogs.size();
        double avgT =sumTotaltime/tourLogs.size();
        averages.add(String.valueOf(avgR));
        averages.add(String.valueOf(avgD));
        averages.add(String.valueOf(avgT));
        dal.reportWriter().createSummaryReport(t.getName(),averages);

    }
    public int calculatePopularity(Tour t) throws SQLException {
        List<TourLog> tourLogs =dal.tourLogDao().getAll(t.getName());
        return tourLogs.size();
    }

    public double calculateChildF(Tour t) throws SQLException {
        List<TourLog> tourLogs = dal.tourLogDao().getAll(t.getName());
        if (tourLogs != null) {
            double sumDifficulty = 0;
            double sumTotaltime = 0;

            for (TourLog x : tourLogs) {
                sumDifficulty += x.getDifficultly();
                sumTotaltime += Integer.parseInt(x.getTotal_time());
            }
            double avgD = sumDifficulty / tourLogs.size();
            double avgT = sumTotaltime / tourLogs.size();

            double child = 1 - ((avgD + avgT + t.getDistance()) / 2100);
            return round(child * 100, 2) < 0 ? 0 : round(child * 100, 2);
        }
        return 0;
    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}

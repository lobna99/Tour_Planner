package com.example.tour_planner.BL;

import com.example.tour_planner.DAL.DAL;
import com.example.tour_planner.logger.ILoggerWrapper;
import com.example.tour_planner.logger.LoggerFactory;
import com.example.tour_planner.model.Tour;
import com.example.tour_planner.model.TourLog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchLogic {
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    private DAL dal;

    public SearchLogic(){
        this(DAL.getInstance());
    }
    //for tests
    SearchLogic(DAL dal){
        this.dal=dal;
    }

    public List<Tour> findMatchingTours(String searchText) {
        try {
            List<Tour> tours = dal.tourDao().getAll("");
            List<TourLog> tourLogs = dal.tourLogDao().getAll("");

            if (searchText==null || searchText.isEmpty()) {
                return tours;
            }
            List<TourLog> matchinglogs = tourLogs.stream().filter(tourLog -> tourLog.getComment().toLowerCase().contains(searchText.toLowerCase())).toList();
            ArrayList<String> matchingNames = new ArrayList<String>();
            matchinglogs.forEach(tourLog -> matchingNames.add(tourLog.getName().toLowerCase()));
            matchingNames.add(searchText.toLowerCase());
            logger.debug(String.valueOf(matchingNames));
            return tours.stream()
                    .filter(x ->matchingNames.contains(x.getName().toLowerCase()))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            logger.fatal(e.toString());
        }
        return  null;
    }

    public boolean nameExists(String name) throws SQLException {
        List<Tour> tours = dal.tourDao().getAll("");
        return tours.stream().anyMatch(s ->s.getName().equals(name.toLowerCase()));
    }
}

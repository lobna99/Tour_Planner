package com.example.tour_planner.BL;
import com.example.tour_planner.DAL.DAL;
import com.example.tour_planner.logger.ILoggerWrapper;
import com.example.tour_planner.logger.LoggerFactory;
import com.example.tour_planner.model.Tour;
import com.example.tour_planner.model.TourLog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class BL {
    private static final ILoggerWrapper logger = LoggerFactory.getLogger();

    public List<Tour> findMatchingTours(String searchText) {
        try {
            List<Tour> tours = DAL.getInstance().tourDao().getAll("");
            List<TourLog> tourLogs = DAL.getInstance().tourLogDao().getAll("");

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
            logger.error(e.toString());
        }
        return  null;
    }



    //
    // Singleton-Pattern for BL with early-binding
    //
    private static BL instance = new BL();

    public static BL getInstance() { return instance; }
}

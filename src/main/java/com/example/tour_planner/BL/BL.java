package com.example.tour_planner.BL;
import com.example.tour_planner.DAL.DAL;
import com.example.tour_planner.logger.ILoggerWrapper;
import com.example.tour_planner.logger.LoggerFactory;
import com.example.tour_planner.model.Tour;
import com.example.tour_planner.model.TourLog;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.lang.Math.round;

public class BL {

    private SearchLogic searchLogic;
    private StatsCalculation statsCalculation;

    private BL(){
        searchLogic = new SearchLogic();
        statsCalculation = new StatsCalculation();
    }
    //
    // Singleton-Pattern for BL with early-binding
    //
    private static BL instance = new BL();

    public static BL getInstance() { return instance; }

    public SearchLogic getSearchLogic() {
        return searchLogic;
    }

    public StatsCalculation getStatsCalculation() {
        return statsCalculation;
    }
}

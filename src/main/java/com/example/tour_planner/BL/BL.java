package com.example.tour_planner.BL;
import com.example.tour_planner.DAL.DAL;
import com.example.tour_planner.model.Tour;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class BL {
    public List<Tour> findMatchingTours(String searchText) {
        try {
            List<Tour> tours = DAL.getInstance().tourDao().getAll("");

        if (searchText==null || searchText.isEmpty()) {
            return tours;
        }
        return tours.stream()
                .filter(t->t.getName().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    //
    // Singleton-Pattern for BL with early-binding
    //
    private static BL instance = new BL();

    public static BL getInstance() { return instance; }
}

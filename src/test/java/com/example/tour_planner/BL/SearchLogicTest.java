package com.example.tour_planner.BL;

import com.example.tour_planner.DAL.DAL;
import com.example.tour_planner.DAL.DAOs.TourDao;
import com.example.tour_planner.DAL.DAOs.TourLogDao;
import com.example.tour_planner.model.Tour;
import com.example.tour_planner.model.TourLog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SearchLogicTest {

    @Mock
    private DAL dal;
    @Mock
    private TourLogDao tourLog;

    @Mock
    private TourDao tour;

    @Test
    void findMatchingTours() {
        List<TourLog> tourLogList = Arrays.asList(new TourLog("2020-22-11","esdfs",1,"33",4,"ee"),new TourLog("2020-25-11","blb",1,"33",4,"e"));
        List<Tour> tours = Arrays.asList(new Tour("1","ee","w","g","33",123,"eee"), new Tour("3","e","e","t","",33,""));
        try {
            Mockito.when(dal.tourLogDao()).thenReturn(tourLog);
            Mockito.when(dal.tourDao()).thenReturn(tour);
            Mockito.when(dal.tourLogDao().getAll(any(String.class))).thenReturn(tourLogList);
            Mockito.when(dal.tourDao().getAll(any(String.class))).thenReturn(tours);
            SearchLogic searchLogic = new SearchLogic(dal);
            assertEquals(List.of(tours.get(0)),searchLogic.findMatchingTours("ee"));
            assertEquals(List.of(tours.get(1)),searchLogic.findMatchingTours("blb"));
            assertNotEquals(List.of(1),searchLogic.findMatchingTours("esdfs"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    void nameExists() {
        List<Tour> tours = Arrays.asList(new Tour("1","ee","w","g","33",123,"eee"), new Tour("3","e","e","t","",33,""));
        try {
            Mockito.when(dal.tourDao()).thenReturn(tour);
            Mockito.when(dal.tourDao().getAll(any(String.class))).thenReturn(tours);
            SearchLogic searchLogic = new SearchLogic(dal);
            assertTrue(searchLogic.nameExists("ee"));
            assertFalse(searchLogic.nameExists("t"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
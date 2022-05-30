package com.example.tour_planner.BL;

import com.example.tour_planner.DAL.DAL;
import com.example.tour_planner.DAL.DAOs.TourDao;
import com.example.tour_planner.DAL.DAOs.TourLogDao;
import com.example.tour_planner.model.Tour;
import com.example.tour_planner.model.TourLog;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatsCalculationTest {



    @Mock
    private DAL dal;
    @Mock
    private TourLogDao tourLog;
    @Mock
    private TourDao tour;
    @InjectMocks
    private BL bl = BL.getInstance();

    @Test
    void childF() {
        List<TourLog> tourLogList = Arrays.asList(new TourLog("2020-22-11","blb",1,"33",4,"e"),new TourLog("2020-25-11","blb",1,"33",4,"e"));

        try {
            Mockito.when(dal.tourLogDao()).thenReturn(tourLog);
            Mockito.when(dal.tourLogDao().getAll(any(String.class))).thenReturn(tourLogList);
            StatsCalculation statsCalculation = new StatsCalculation(dal);
            assertEquals(98.33,statsCalculation.calculateChildF(new Tour("","","","","",1,"")));
            assertNotEquals(98.3,statsCalculation.calculateChildF(new Tour("","","","","",1,"")));
        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    void calculatePopularity() throws Exception {
        List<TourLog> tourLogList = Arrays.asList(new TourLog("2020-22-11","blb",1,"33",4,"e"),new TourLog("2020-25-11","blb",1,"33",4,"e"));

        try {
            Mockito.when(dal.tourLogDao()).thenReturn(tourLog);
            Mockito.when(dal.tourLogDao().getAll(any(String.class))).thenReturn(tourLogList);
            StatsCalculation statsCalculation = new StatsCalculation(dal);
            assertEquals(2,statsCalculation.calculatePopularity(new Tour("d","o","d","3","er",0,"e")));
            assertNotEquals(0,statsCalculation.calculatePopularity(new Tour("d","o","d","3","er",0,"e")));
        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    void round() {
        assertEquals(23.46,StatsCalculation.round(23.4567,2));
        assertNotEquals(23.345,StatsCalculation.round(23.3456,4));
      }
}
package com.example.tour_planner.viewmodel;

import com.example.tour_planner.model.Tour;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class TourOverviewViewModelTest {

    @Test
    void setTours() {
        TourOverviewViewModel tourOverviewViewModel = new TourOverviewViewModel();
        tourOverviewViewModel.setTours(Arrays.asList(new Tour("1","dsas","123","","",4,""),new Tour("1","dsas","123","","",4,"")));
        assertEquals(2,tourOverviewViewModel.getObservableTours().size());
        assertNotEquals(0,tourOverviewViewModel.getObservableTours().size());

    }
}
package com.example.tour_planner.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TourLogTest {

    private TourLog tourLog = new TourLog("30","looool",3,"23",5,"test");

    @Test
    void getComment() {
        assertEquals("looool",tourLog.getComment());
        assertEquals("looool",tourLog.getCommentProperty().get());
    }

    @Test
    void getTimeProperty() {
        assertNotEquals("30", tourLog.getTimeProperty());
        assertEquals("30",tourLog.getTime());
        assertEquals("30",tourLog.getTimeProperty().get());
        assertNotEquals("31",tourLog.getTime());
    }

    @Test
    void setTime() {
        assertEquals("30",tourLog.getTime());
        tourLog.setTime("33");
        assertEquals("33",tourLog.getTime());
        assertNotEquals("30",tourLog.getTime());
        assertEquals("33",tourLog.getTimeProperty().get());
    }

    @Test
    void setId() {
        tourLog.setId(11);
        assertEquals(11,tourLog.getId());
        assertNotEquals(2,tourLog.getId());
        tourLog.setId(3);
        assertNotEquals(11,tourLog.getId());
        assertEquals(3,tourLog.getId());
    }


    @Test
    void getId() {
        tourLog.setId(11);
        assertEquals(11,tourLog.getId());
    }

}
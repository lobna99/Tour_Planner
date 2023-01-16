package com.example.tour_planner.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TourTest {

    private Tour tour = new Tour("car","lol","wien","graz","23",123,"hehehe");


    @Test
    void getContent() {
        assertEquals("hehehe",tour.getContent());
    }

    @Test
    void getDistance() {
        assertEquals(123,tour.getDistance());
    }

    @Test
    void getDuration() {
        assertEquals("23",tour.getDuration());
    }

    @Test
    void getTo() {
        assertEquals("graz",tour.getTo());
    }

    @Test
    void getFrom() {
        assertEquals("wien",tour.getFrom());
    }

    @Test
    void getName() {
        assertEquals("lol",tour.getName());
    }

    @Test
    void getTransport_type() {
        assertEquals("car",tour.getTransport_type());
    }
}
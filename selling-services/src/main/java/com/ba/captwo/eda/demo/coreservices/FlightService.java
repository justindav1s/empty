package com.ba.captwo.eda.demo.coreservices;

import com.ba.captwo.eda.demo.model.Flight;

import java.util.ArrayList;

/**
 * Created by u760245 on 14/07/2014.
 */
public interface FlightService {

    public Flight createFlight(Flight f);
    public Flight readFlight(String flightnum);
    public Flight updateFlight(Flight f);
    public void deleteFlight(String flightnum);
    public ArrayList<Flight> listFlights();

}

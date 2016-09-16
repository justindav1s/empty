package com.ba.captwo.eda.demo.coreservices;

import com.ba.captwo.eda.demo.db.FlightDAO;
import com.ba.captwo.eda.demo.db.PersonDAO;
import com.ba.captwo.eda.demo.model.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by u760245 on 14/07/2014.
 */
@Component("FlightCoreServiceBean")
public class FlightServiceImpl implements FlightService {

    private final Logger log = LoggerFactory.getLogger(FlightService.class);

    @Autowired
    FlightDAO flightDAO;


    @Override
    public Flight createFlight(Flight f) {
        log.debug("createFlight");
        return flightDAO.createFlight(f);
    }

    @Override
    public Flight readFlight(String flightnum) {
        log.debug("readFlight");
        return flightDAO.readFlight(flightnum);
    }

    @Override
    public Flight updateFlight(Flight f) {
        log.debug("updateFlight");
        return flightDAO.updateFlight(f);
    }

    @Override
    public void deleteFlight(String flightnum) {
        log.debug("deleteFlight");
        flightDAO.deleteFlight(flightnum);

    }

    @Override
    public ArrayList<Flight> listFlights() {
        log.debug("listFlights");
        return flightDAO.listFlights();
    }
}

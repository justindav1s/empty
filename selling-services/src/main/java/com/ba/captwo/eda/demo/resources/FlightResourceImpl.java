package com.ba.captwo.eda.demo.resources;

import com.ba.captwo.eda.demo.coreservices.FlightService;
import com.ba.captwo.eda.demo.db.FlightDAO;
import com.ba.captwo.eda.demo.model.Error;
import com.ba.captwo.eda.demo.model.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by u760245 on 05/07/2014.
 */
@Component("FlightResource")
public class FlightResourceImpl implements FlightResource {

    private final Logger log = LoggerFactory.getLogger(FlightResourceImpl.class);

    @Autowired
    FlightService flightService;

    public Response createFlight(
            String fnum,
            String orig,
            String dest)    {

        log.debug("createFlight");
        log.debug("fnum : " + fnum);
        log.debug("orig : " + orig);
        log.debug("dest : " + dest);

        Response response = null;

        if ((fnum == null) || (orig == null) || (dest == null))  {
            Error err = new Error();
            err.setMessage("Incomplate Request");
            return Response.status(Response.Status.BAD_REQUEST).entity(err).build();
        }

        Flight p = new Flight();
        p.setFlightnum(fnum);
        p.setOrigin(orig);
        p.setDestination(dest);

        try {
            p = flightService.createFlight(p);
            response = Response.status(Response.Status.OK).entity(p).build();
        }
        catch (Exception e) {
            Error err = new Error();
            err.setMessage(e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }

        return response;
    }

    public Response readFlight(String fnum)    {

        log.debug("deleteFlight");
        log.debug("fnum : " + fnum);

        Response response = null;

        if ((fnum == null))  {
            Error err = new Error();
            err.setMessage("Incomplate Request");
            return Response.status(Response.Status.BAD_REQUEST).entity(err).build();
        }

        Flight p = null;

        try {
            p = flightService.readFlight(fnum);
            response = Response.status(Response.Status.OK).entity(p).build();
        }
        catch (Exception e) {
            Error err = new Error();
            err.setMessage(e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }

        return response;
    }


    public Response updateFlight(
            String fnum,
            String orig,
            String dest)    {

        log.debug("updateFlight");
        log.debug("fnum : " + fnum);
        log.debug("orig : " + orig);
        log.debug("dest : " + dest);

        Response response = null;

        if ((fnum == null) || (orig == null) || (dest == null))  {
            Error err = new Error();
            err.setMessage("Incomplate Request");
            return Response.status(Response.Status.BAD_REQUEST).entity(err).build();
        }

        Flight p = new Flight();
        p.setFlightnum(fnum);
        p.setOrigin(orig);
        p.setDestination(dest);

        try {
            p = flightService.updateFlight(p);
            response = Response.status(Response.Status.OK).entity(p).build();
        }
        catch (Exception e) {
            Error err = new Error();
            err.setMessage(e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }

        return response;
    }

    public Response deleteFlight(String fnum)    {

        log.debug("deleteFlight");
        log.debug("fnum : " + fnum);

        Response response = null;

        if ((fnum == null))  {
            Error err = new Error();
            err.setMessage("Incomplate Request");
            return Response.status(Response.Status.BAD_REQUEST).entity(err).build();
        }

        Flight p = null;

        try {
            flightService.deleteFlight(fnum);
            response = Response.status(Response.Status.OK).entity("OK").build();
        }
        catch (Exception e) {
            Error err = new Error();
            err.setMessage(e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }

        return response;
    }

    public Response listFlights()    {

        Response response = null;

        Flight p = null;

        try {
            ArrayList<Flight> flights = flightService.listFlights();
            response = Response.status(Response.Status.OK).entity(flights).build();
        }
        catch (Exception e) {
            Error err = new Error();
            err.setMessage(e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }

        return response;
    }
}

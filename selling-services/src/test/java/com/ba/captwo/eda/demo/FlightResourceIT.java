package com.ba.captwo.eda.demo;

import com.ba.captwo.eda.demo.db.FlightDAO;
import com.ba.captwo.eda.demo.model.Flight;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test_beans.xml"})
public class FlightResourceIT {
    private static String endpointUrl;
    private final static Logger log = LoggerFactory.getLogger(FlightResourceIT.class);
    @Autowired
    FlightDAO flightDAO;

    @BeforeClass
    public static void beforeClass() {
        endpointUrl = System.getProperty("service.url");
    }

    @Test
    public void testCreate() throws Exception {
        log.info("Test CREATE");

        Flight f = buildFlight();
        flightDAO.deleteFlight(f.getFlightnum());

        String uri = "/flight/create;fnum="+f.getFlightnum()+";orig="+f.getOrigin()+";dest="+f.getDestination();

        WebClient client = WebClient.create(endpointUrl + uri);
        Response r = client.accept("application/json").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("Create response : "+value);

        ObjectMapper mapper = new ObjectMapper();
        Flight f1 = mapper.readValue(value, Flight.class);
        log.info("Created Flightnum : "+f1.getFlightnum());

        Flight f2 = flightDAO.readFlight(f1.getFlightnum());
        assertEquals(f.getOrigin(), f2.getOrigin());
        assertEquals(f.getDestination(), f2.getDestination());

        flightDAO.deleteFlight(f1.getFlightnum());
    }

    @Test
    public void testRead() throws Exception {
        log.info("Test READ");

        Flight f = buildFlight();
        flightDAO.deleteFlight(f.getFlightnum());
        f = flightDAO.createFlight(f);

        WebClient client = WebClient.create(endpointUrl + "/flight/read;fnum="+f.getFlightnum());
        Response r = client.accept("application/json").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("Read response : "+value);
        ObjectMapper mapper = new ObjectMapper();
        Flight f1 = mapper.readValue(value, Flight.class);
        log.info("Read Flightnum : "+f1.getFlightnum());
        assertEquals(f.getOrigin(), f1.getOrigin());
        assertEquals(f.getDestination(), f1.getDestination());

        flightDAO.deleteFlight(f.getFlightnum());
    }

    @Test
    public void testUpdate() throws Exception {
        log.info("Test UPDATE");

        String oldDest = "JFK";
        String newDest = "NYC";

        Flight f = buildFlight();
        flightDAO.deleteFlight(f.getFlightnum());
        f = flightDAO.createFlight(f);

        String uri = "/flight/update;fnum="+f.getFlightnum()+";orig="+f.getOrigin()+";dest="+newDest;

        WebClient client = WebClient.create(endpointUrl + uri);
        Response r = client.accept("application/json").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("Update response : "+value);

        ObjectMapper mapper = new ObjectMapper();
        Flight f1 = mapper.readValue(value, Flight.class);
        log.info("Update fnum : "+f1.getFlightnum());
        log.info("Updated dest : "+f1.getDestination());
        Assert.assertEquals(newDest, f1.getDestination());

        Flight f2 = flightDAO.readFlight(f.getFlightnum());
        Assert.assertEquals(newDest, f2.getDestination());

        flightDAO.deleteFlight(f.getFlightnum());

    }


    @Test
    public void testDelete() throws Exception {
        log.info("Test DELETE");

        Flight f = buildFlight();
        flightDAO.deleteFlight(f.getFlightnum());
        f = flightDAO.createFlight(f);

        WebClient client = WebClient.create(endpointUrl + "/flight/delete;fnum="+f.getFlightnum());
        Response r = client.accept("application/json").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("Delete response : "+value);
        assertEquals("OK", value);

        flightDAO.deleteFlight(f.getFlightnum());
    }

    @Test
    public void testList() throws Exception {
        log.info("Test LIST");

        Flight f = buildFlight();
        flightDAO.deleteFlight(f.getFlightnum());
        f = flightDAO.createFlight(f);

        WebClient client = WebClient.create(endpointUrl + "/flight/list");
        Response r = client.accept("application/json").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("List response : "+value);

        ObjectMapper mapper = new ObjectMapper();
        Flight[] flights = mapper.readValue(value, Flight[].class);
        assertTrue(flights.length > 0);

        flightDAO.deleteFlight(f.getFlightnum());
    }

    public Flight buildFlight() {
        Flight f = new Flight();
        f.setFlightnum("BA177");
        f.setOrigin("LHR");
        f.setDestination("JFK");
        return f;
    }
}

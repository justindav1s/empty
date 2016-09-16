package com.ba.captwo.eda.demo;

import com.ba.captwo.eda.demo.db.BookingDAO;
import com.ba.captwo.eda.demo.model.Booking;
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
public class BookingResourceIT {
    private static String endpointUrl;
    private final static Logger log = LoggerFactory.getLogger(BookingResourceIT.class);
    @Autowired
    BookingDAO bookingDAO;

    @BeforeClass
    public static void beforeClass() {
        endpointUrl = System.getProperty("service.url");
    }

    @Test
    public void testCreate() throws Exception {
        log.info("Test CREATE");

        Booking b = new Booking();
        b.setPersonID(1);
        b.setFlightNum("BA009");
        b.setTickets(2);
        b.setCabin("J");

        String uri = "/booking/create;pid="+b.getPersonID()+";fnum="+b.getFlightNum()+";ticks="+b.getTickets()+";cab="+b.getCabin();

        WebClient client = WebClient.create(endpointUrl + uri);
        Response r = client.accept("application/json").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("Create response : "+value);

        ObjectMapper mapper = new ObjectMapper();
        Booking b2 = mapper.readValue(value, Booking.class);
        log.info("Create Booking pnr : "+b2.getBookingId());

        bookingDAO.deleteBooking(b2.getBookingId());
    }

    @Test
    public void testRead() throws Exception {
        log.info("Test READ");

        Booking b = new Booking();
        b.setPersonID(1);
        b.setFlightNum("BA009");
        b.setTickets(2);
        b.setCabin("J");

        b = bookingDAO.createBooking(b);

        log.info("Test retreiving booking : "+b.getBookingId());
        WebClient client = WebClient.create(endpointUrl + "/booking/read;pnr="+b.getBookingId());
        Response r = client.accept("application/json").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("Read response : "+value);

        bookingDAO.deleteBooking(b.getBookingId());
    }

    @Test
    public void testUpdate() throws Exception {
        log.info("Test UPDATE");

        String oldCab = "J";
        String newCab = "F";

        Booking b = new Booking();
        b.setPersonID(1);
        b.setFlightNum("BA009");
        b.setTickets(2);
        b.setCabin(oldCab);

        b = bookingDAO.createBooking(b);

        log.info("Test updating booking : "+b.toString());
        String uri = "/booking/update;pnr="+b.getBookingId()+";pid="+b.getPersonID()+";fnum="+b.getFlightNum()+";ticks="+b.getTickets()+";cab="+newCab;

        WebClient client = WebClient.create(endpointUrl + uri);
        Response r = client.accept("application/json").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("Update response : "+value);

        ObjectMapper mapper = new ObjectMapper();
        Booking p = mapper.readValue(value, Booking.class);
        log.info("Update pnr : "+p.getBookingId());
        log.info("Updated fnum : "+p.getFlightNum());
        Assert.assertEquals(newCab, p.getCabin());

        Booking p2 = bookingDAO.readBooking(p.getBookingId());
        Assert.assertEquals(newCab, p2.getCabin());

        bookingDAO.deleteBooking(p.getBookingId());

    }


    @Test
    public void testDelete() throws Exception {
        log.info("Test DELETE");

        Booking b = new Booking();
        b.setPersonID(1);
        b.setFlightNum("BA009");
        b.setTickets(2);
        b.setCabin("J");

        b = bookingDAO.createBooking(b);

        WebClient client = WebClient.create(endpointUrl + "/booking/delete;pnr="+b.getBookingId());
        Response r = client.accept("application/json").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("Delete response : "+value);
        assertEquals("OK", value);

        bookingDAO.deleteBooking(b.getBookingId());
    }

    @Test
    public void testList() throws Exception {
        log.info("Test LIST");

        Booking b = new Booking();
        b.setPersonID(1);
        b.setFlightNum("BA009");
        b.setTickets(2);
        b.setCabin("J");

        b = bookingDAO.createBooking(b);

        WebClient client = WebClient.create(endpointUrl + "/booking/list");
        Response r = client.accept("application/json").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("List response : "+value);
        ObjectMapper mapper = new ObjectMapper();

        Booking[] bookings = mapper.readValue(value, Booking[].class);
        assertTrue(bookings.length > 0);

        bookingDAO.deleteBooking(b.getBookingId());
    }
}

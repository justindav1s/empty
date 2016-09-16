package com.ba.captwo.eda.demo.db;

import com.ba.captwo.eda.demo.model.Booking;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by u760245 on 04/07/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test_beans.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class BookingDAOIT {

    private final static Logger log = LoggerFactory.getLogger(BookingDAOIT.class);

    @Autowired
    DAOUtils daoUtils;
    @Autowired
    DataSource sellingDatasource;
    @Autowired
    BookingDAO bookingDAO;

    private static Booking testBooking = null;
    private static Booking testBooking2 = null;

    @Before
    public void setUp() throws Exception {

        log.info("setUp");
        //createSequence();
        //createTable();

        log.info("buildTestBookings");
        testBooking = new Booking();
        testBooking.setPersonID(1);
        testBooking.setFlightNum("BA009");
        testBooking.setTickets(2);
        testBooking.setCabin("J");


        testBooking2 = new Booking();
        testBooking2.setPersonID(2);
        testBooking2.setFlightNum("BA175");
        testBooking2.setTickets(2);
        testBooking2.setCabin("F");

        bookingDAO.deleteBooking(testBooking.getBookingId());
        bookingDAO.deleteBooking(testBooking2.getBookingId());
    }

    @After
    public void tearDown() throws Exception {
        log.info("tearDown");
        //dropSequence();
        //dropTable();
    }


    @Test
    public void testCreateReadDelete() throws Exception {

        log.info("testCreate");

        Booking b = bookingDAO.createBooking(testBooking);
        log.info("b : "+b.toString());
        Assert.assertNotNull(b);
        Booking b2 = bookingDAO.readBooking(b.getBookingId());
        log.info("b2 : "+b2.toString());
        Assert.assertNotNull(b2);
        Assert.assertEquals(testBooking.getFlightNum(), b2.getFlightNum());
        Assert.assertEquals(b.getBookingId(), b2.getBookingId());

        bookingDAO.deleteBooking(b.getBookingId());
        Booking b3 = bookingDAO.readBooking(b.getBookingId());
        Assert.assertEquals(0, b3.getBookingId());
        Assert.assertNull(b3.getCabin());
        Assert.assertNull(b3.getFlightNum());
    }


    @Test
    public void testUpdate() throws Exception {
        log.info("testUpdate");
        String oldFlightnum = "BA009";
        String newFlightnum = "BA50";


        Booking b = bookingDAO.createBooking(testBooking);
        Assert.assertNotNull(b);

        Booking b1 = bookingDAO.readBooking(b.getBookingId());
        Assert.assertNotNull(b1);
        Assert.assertEquals(testBooking.getBookingId(), b1.getBookingId());
        Assert.assertEquals(testBooking.getPersonID(), b1.getPersonID());
        Assert.assertEquals(testBooking.getCabin(), b1.getCabin());
        Assert.assertEquals(testBooking.getTickets(), b1.getTickets());
        Assert.assertEquals(oldFlightnum, b1.getFlightNum());


        b1.setFlightNum(newFlightnum);

        Booking b2 = bookingDAO.updateBooking(b1);
        Assert.assertNotNull(b2);
        Assert.assertEquals(b1.getBookingId(), b2.getBookingId());
        Assert.assertEquals(testBooking.getPersonID(), b2.getPersonID());
        Assert.assertEquals(testBooking.getCabin(), b2.getCabin());
        Assert.assertEquals(testBooking.getTickets(), b2.getTickets());
        Assert.assertEquals(newFlightnum, b2.getFlightNum());

        bookingDAO.deleteBooking(b.getBookingId());

    }

    @Test
    public void testList() throws Exception {

        log.info("testList");
        daoUtils.truncateTable("bookings");
        ArrayList<Booking> bookings = bookingDAO.listBookings();
        log.info("booking count : "+bookings.size());
        Assert.assertEquals(0, bookings.size());

        bookingDAO.createBooking(testBooking);
        bookingDAO.createBooking(testBooking2);
        bookings = bookingDAO.listBookings();
        log.info("booking count : "+bookings.size());
        Assert.assertEquals(2, bookings.size());

        bookingDAO.deleteBooking(testBooking.getBookingId());
        bookingDAO.deleteBooking(testBooking2.getBookingId());
    }


    private void dropTable()   {
        log.debug("dropTable");
        String sql = "DROP TABLE bookings";
        log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                st.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void createTable()   {
        log.debug("createTable");
        String sql = "CREATE TABLE bookings (PersonID int,BookingId int,flightnum varchar(255),tickets int,cabin  varchar(255), PRIMARY KEY (BookingId))";
        log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) st.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dropSequence()   {
        log.debug("dropSequence");
        String sql = "DROP SEQUENCE BA_SEQ RESTRICT";
        log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) st.close();
                if (conn != null) conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createSequence()   {
        log.debug("createSequence");
        String sql = "CREATE SEQUENCE BA_SEQ AS INT MINVALUE 1000 MAXVALUE 999999 CYCLE";
        log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) st.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

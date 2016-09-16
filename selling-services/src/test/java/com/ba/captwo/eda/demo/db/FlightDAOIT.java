package com.ba.captwo.eda.demo.db;

import com.ba.captwo.eda.demo.model.Flight;
import org.junit.*;
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
public class FlightDAOIT {

    private final static Logger log = LoggerFactory.getLogger(FlightDAOIT.class);

    @Autowired
    DAOUtils daoUtils;
    @Autowired
    DataSource sellingDatasource;
    @Autowired
    FlightDAO flightDAO;

    private static Flight testFlight = null;
    private static Flight testFlight2 = null;

    @Before
    public void setUp() throws Exception {

        log.info("setUp");
        //createTable();

        log.info("buildTestFlights");
        testFlight = new Flight();
        testFlight.setFlightnum("BA009");
        testFlight.setOrigin("LHR");
        testFlight.setDestination("BKK");

        testFlight2 = new Flight();
        testFlight2.setFlightnum("BA175");
        testFlight2.setOrigin("LHR");
        testFlight2.setDestination("JFK");
    }

    @After
    public void tearDown() throws Exception {
        log.info("tearDown");
        //dropTable();
    }


    @Test
    public void testCreateReadDelete() throws Exception {

        log.info("testCreate");

        Flight f = flightDAO.createFlight(testFlight);
        Assert.assertNotNull(f);
        Flight f2 = flightDAO.readFlight(testFlight.getFlightnum());
        Assert.assertNotNull(f2);
        Assert.assertEquals("LHR", f2.getOrigin());
        Assert.assertEquals("BKK", f2.getDestination());

        flightDAO.deleteFlight(testFlight.getFlightnum());
        Flight f3 = flightDAO.readFlight(testFlight.getFlightnum());
        Assert.assertEquals("UNKNOWN", f3.getFlightnum());
        Assert.assertNull(f3.getOrigin());
        Assert.assertNull(f3.getDestination());
    }


    @Test
    public void testUpdate() throws Exception {
        log.info("testUpdate");
        String newDest = "HKG";
        String oldDest = "BKK";

        Flight f = flightDAO.createFlight(testFlight);
        Assert.assertNotNull(f);

        Flight f1 = flightDAO.readFlight(testFlight.getFlightnum());
        Assert.assertNotNull(f1);
        Assert.assertEquals("LHR", f1.getOrigin());
        Assert.assertEquals(oldDest, f1.getDestination());

        testFlight.setDestination(newDest);

        Flight f2 = flightDAO.updateFlight(testFlight);
        Assert.assertNotNull(f2);
        Assert.assertEquals("LHR", f2.getOrigin());
        Assert.assertEquals(newDest, f2.getDestination());

        Flight f3 = flightDAO.readFlight(testFlight.getFlightnum());
        Assert.assertNotNull(f3);
        log.info("f3 : " + f3.toString());
        Assert.assertEquals("LHR", f3.getOrigin());
        Assert.assertEquals(newDest, f3.getDestination());

        testFlight.setDestination(oldDest);

        Flight f4 = flightDAO.updateFlight(testFlight);
        Assert.assertNotNull(f4);
        Assert.assertEquals("LHR", f4.getOrigin());
        Assert.assertEquals(oldDest, f4.getDestination());

    }

    @Test
    public void testList() throws Exception {

        log.info("testList");
        daoUtils.truncateTable("flights");
        flightDAO.deleteFlight(testFlight.getFlightnum());
        flightDAO.deleteFlight(testFlight2.getFlightnum());
        ArrayList<Flight> flights = flightDAO.listFlights();
        log.info("flight count : "+flights.size());
        Assert.assertEquals(0, flights.size());

        flightDAO.createFlight(testFlight);
        flightDAO.createFlight(testFlight2);
        flights = flightDAO.listFlights();
        log.info("flight count : "+flights.size());
        Assert.assertEquals(2, flights.size());

        flightDAO.deleteFlight(testFlight.getFlightnum());
        flightDAO.deleteFlight(testFlight2.getFlightnum());
    }


    private void dropTable()   {
        log.debug("dropTable");
        String sql = "DROP TABLE flights";
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
        String sql = "CREATE TABLE flights(flightnum varchar(255),origin varchar(255),destination varchar(255),PRIMARY KEY (flightnum))";
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

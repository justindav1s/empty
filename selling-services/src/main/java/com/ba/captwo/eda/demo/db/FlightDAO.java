package com.ba.captwo.eda.demo.db;

import com.ba.captwo.eda.demo.coreservices.FlightService;
import com.ba.captwo.eda.demo.model.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by u760245 on 04/07/2014.
 */
@Component("FlightDAO")
public class FlightDAO {

    private final Logger log = LoggerFactory.getLogger(FlightDAO.class);

    @Autowired
    private DataSource sellingDatasource;
    @Autowired
    DAOUtils daoUtils;

    public Flight createFlight(Flight f)   {

        log.debug("createFlight");

        String sql = "INSERT INTO FLIGHTS (flightnum, origin, destination) values (?, ?, ?)";
        //log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        int nextInSeq = 0;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, f.getFlightnum());
            st.setString(2, f.getOrigin());
            st.setString(3, f.getDestination());

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
        return f;
    }

    public Flight readFlight(String flightnum)   {

        log.debug("readFlight");

        String sql = "SELECT * FROM FLIGHTS where flightnum = ?";
        //log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        int nextInSeq = 0;
        Flight f = new Flight();;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, flightnum);

            rs = st.executeQuery();
            if (rs.next())  {
                f.setFlightnum(flightnum);
                f.setOrigin(rs.getString("origin"));
                f.setDestination(rs.getString("destination"));
            }
            else    {
                f.setFlightnum("UNKNOWN");
            }

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
        return f;
    }

    public Flight updateFlight(Flight f)   {

        log.debug("updateFlight");
        //log.info("Before : "+f.toString());
        String sql = "UPDATE FLIGHTS SET origin = ?, destination = ? where flightnum = ?";
        //log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        int nextInSeq = 0;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, f.getOrigin());
            st.setString(2, f.getDestination());
            st.setString(3, f.getFlightnum());
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
        //log.info("After : "+f.toString());
        return f;
    }

    public void deleteFlight(String flightnum)   {

        log.debug("deleteFlight");

        String sql = "DELETE FROM FLIGHTS where flightnum = ?";
        //log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, flightnum);
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

    public ArrayList<Flight> listFlights()   {

        log.debug("listFlights");

        ArrayList<Flight> Flights = new ArrayList<Flight>();
        String sql = "SELECT * FROM FLIGHTS";
        //log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        int nextInSeq = 0;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next())  {
                Flight f = new Flight();
                f.setFlightnum(rs.getString("flightnum"));
                f.setOrigin(rs.getString("origin"));
                f.setDestination(rs.getString("destination"));
                Flights.add(f);
            }
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
        return Flights;
    }
}

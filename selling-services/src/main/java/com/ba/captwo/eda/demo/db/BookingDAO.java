package com.ba.captwo.eda.demo.db;

import com.ba.captwo.eda.demo.model.Booking;
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
@Component("BookingDAO")
public class BookingDAO {

    private final Logger log = LoggerFactory.getLogger(BookingDAO.class);

    @Autowired
    private DataSource sellingDatasource;
    @Autowired
    DAOUtils daoUtils;

    public Booking createBooking(Booking b)   {

        //daoUtils.listTables();

        String sql = "INSERT INTO BOOKINGS (PersonID, BookingID, flightNum, tickets, cabin) values (?, ?, ?, ?, ?)";
        //log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        int nextInSeq = 0;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            b.setBookingId(daoUtils.getNextId());
            st.setInt(1, b.getPersonID());
            st.setInt(2, b.getBookingId());
            st.setString(3, b.getFlightNum());
            st.setInt(4, b.getTickets());
            st.setString(5, b.getCabin());

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
        return b;
    }

    public Booking readBooking(int bookingnum)   {

        String sql = "SELECT * FROM BOOKINGS where BookingID = ?";
        //log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        int nextInSeq = 0;
        Booking b = new Booking();
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, bookingnum);
            rs = st.executeQuery();
            if (rs.next())  {
                b.setBookingId(rs.getInt("bookingId"));
                b.setPersonID(rs.getInt("personId"));
                b.setFlightNum(rs.getString("flightnum"));
                b.setTickets(rs.getInt("tickets"));
                b.setCabin(rs.getString("cabin"));
            }
            else    {
                b.setBookingId(0);
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
        return b;
    }

    public Booking readBookingByFlightnum(String flightnum)   {

        String sql = "SELECT * FROM BOOKINGS where flightnum = ?";
        //log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        int nextInSeq = 0;
        Booking b = new Booking();
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, flightnum);
            rs = st.executeQuery();
            if (rs.next())  {
                b.setBookingId(rs.getInt("bookingId"));
                b.setPersonID(rs.getInt("personId"));
                b.setFlightNum(rs.getString("flightnum"));
                b.setTickets(rs.getInt("tickets"));
                b.setCabin(rs.getString("cabin"));
            }
            else    {
                b.setBookingId(0);
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
        return b;
    }

    public Booking updateBooking(Booking b)   {

        String sql = "UPDATE BOOKINGS SET PersonId = ?, flightnum = ?, tickets = ?, cabin = ? where BookingID = ?";
        //log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        int nextInSeq = 0;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(5, b.getBookingId());
            st.setInt(1, b.getPersonID());
            st.setString(2, b.getFlightNum());
            st.setInt(3, b.getTickets());
            st.setString(4, b.getCabin());
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
        return b;
    }

    public void deleteBooking(int bookingNum)   {

        String sql = "DELETE FROM BOOKINGS where BookingId = ?";
        //log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, bookingNum);
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

    public ArrayList<Booking> listBookings()   {

        ArrayList<Booking> bookings = new ArrayList<Booking>();
        String sql = "SELECT * FROM BOOKINGS";
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
                Booking b = new Booking();
                b.setBookingId(rs.getInt("BookingId"));
                b.setPersonID(rs.getInt("personId"));
                b.setFlightNum(rs.getString("flightnum"));
                b.setTickets(rs.getInt("tickets"));
                b.setCabin(rs.getString("cabin"));
                bookings.add(b);
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
        return bookings;
    }
}

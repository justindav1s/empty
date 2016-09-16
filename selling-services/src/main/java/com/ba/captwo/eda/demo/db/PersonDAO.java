package com.ba.captwo.eda.demo.db;

import com.ba.captwo.eda.demo.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by u760245 on 04/07/2014.
 */
@Component("PersonDAO")
public class PersonDAO {

    private final Logger log = LoggerFactory.getLogger(PersonDAO.class);

    @Autowired
    private DataSource sellingDatasource;
    @Autowired
    DAOUtils daoUtils;

    public Person createPerson(Person p)   {

        String sql = "INSERT INTO PERSONS (PersonID, lastName, firstName, address, city) values (?, ?, ?, ?, ?)";
        //log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        int nextInSeq = 0;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            p.setPersonID(daoUtils.getNextId());
            log.debug("New Person with : "+ p.getPersonID());
            st.setInt(1, p.getPersonID());
            st.setString(2, p.getLastName());
            st.setString(3, p.getFirstName());
            st.setString(4, p.getAddress());
            st.setString(5, p.getCity());

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
        return p;
    }

    public Person readPerson(int personid)   {

        log.debug("***THREAD : "+ Thread.currentThread().toString());

        String sql = "SELECT * FROM PERSONS where PersonID = ?";
        //log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        int nextInSeq = 0;
        Person p = new Person();
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, personid);

            rs = st.executeQuery();
            if (rs.next())  {
                p.setPersonID(rs.getInt("personId"));
                p.setLastName(rs.getString("lastName"));
                p.setFirstName(rs.getString("firstName"));
                p.setAddress(rs.getString("address"));
                p.setCity(rs.getString("city"));
            }
            else    {
                p.setPersonID(0);
            }

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
        return p;
    }

    public Person updatePerson(Person p)   {

        String sql = "UPDATE PERSONS SET lastName = ?, firstName = ?, address = ?, city = ? where PersonID = ?";
        //log.debug("Datasource : "+ sellingDatasource);
        log.info("UPDATING PERSON : "+p.toString());
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        int nextInSeq = 0;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(5, p.getPersonID());
            st.setString(1, p.getLastName());
            st.setString(2, p.getFirstName());
            st.setString(3, p.getAddress());
            st.setString(4, p.getCity());
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
        return p;
    }

    public void deletePerson(int personId)   {

        String sql = "DELETE FROM PERSONS where PersonID = ?";
        //log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        int nextInSeq = 0;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            st.setInt(1, personId);
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

    public ArrayList<Person> listPersons()   {

        ArrayList<Person> persons = new ArrayList<Person>();
        String sql = "SELECT * FROM PERSONS";
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
                Person p = new Person();
                p.setPersonID(rs.getInt("PersonID"));
                p.setLastName(rs.getString("lastName"));
                p.setFirstName(rs.getString("firstName"));
                p.setAddress(rs.getString("address"));
                p.setCity(rs.getString("city"));
                persons.add(p);
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
        return persons;
    }
}

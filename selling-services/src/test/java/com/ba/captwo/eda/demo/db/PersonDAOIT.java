package com.ba.captwo.eda.demo.db;

import com.ba.captwo.eda.demo.model.Person;
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
public class PersonDAOIT {

    private final static Logger log = LoggerFactory.getLogger(PersonDAOIT.class);

    @Autowired
    DAOUtils daoUtils;
    @Autowired
    DataSource sellingDatasource;
    @Autowired
    PersonDAO personDAO;

    private static Person testPerson = null;
    private static Person testPerson2 = null;

    @Before
    public void setUp() throws Exception {

        log.info("setUp");
        //createSequence();
        //createTable();

        log.info("buildtestPersons");
        testPerson = new Person();
        testPerson.setLastName("Windsor");
        testPerson.setFirstName("William");
        testPerson.setAddress("Kensington Palace");

        testPerson2 = new Person();
        testPerson2.setLastName("Windor");
        testPerson2.setFirstName("Kate");
        testPerson2.setAddress("Kensington Palace");

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

        Person p = personDAO.createPerson(testPerson);
        log.info("p : "+p.toString());
        Assert.assertNotNull(p);
        Person p2 = personDAO.readPerson(p.getPersonID());
        log.info("p2 : "+p2.toString());
        Assert.assertNotNull(p2);
        Assert.assertEquals(testPerson.getFirstName(), p2.getFirstName());
        Assert.assertEquals(testPerson.getLastName(), p2.getLastName());
        Assert.assertEquals(testPerson.getAddress(), p2.getAddress());

        personDAO.deletePerson(p.getPersonID());
        Person p3 = personDAO.readPerson(p.getPersonID());
        Assert.assertEquals(0, p3.getPersonID());
        Assert.assertNull(p3.getFirstName());
        Assert.assertNull(p3.getLastName());
    }


    @Test
    public void testUpdate() throws Exception {
        log.info("testUpdate");
        String oldLastname = "Windsor";
        String newLastname = "Cambridge";


        Person p = personDAO.createPerson(testPerson);
        Assert.assertNotNull(p);

        Person p1 = personDAO.readPerson(p.getPersonID());
        Assert.assertNotNull(p1);
        Assert.assertEquals(testPerson.getFirstName(), p1.getFirstName());
        Assert.assertEquals(oldLastname, p1.getLastName());

        p.setLastName(newLastname);

        Person p2 = personDAO.updatePerson(p);
        Assert.assertNotNull(p2);
        Assert.assertEquals(testPerson.getFirstName(), p2.getFirstName());
        Assert.assertEquals(newLastname, p2.getLastName());

        personDAO.deletePerson(p.getPersonID());

    }

    @Test
    public void testList() throws Exception {

        log.info("testList");
        ArrayList<Person> persons = null;
        daoUtils.truncateTable("persons");
        Person p = personDAO.createPerson(testPerson);
        Person p2 = personDAO.createPerson(testPerson2);
        persons = personDAO.listPersons();
        log.info("persons count : "+persons.size());
        Assert.assertTrue(persons.size() > 0);

        personDAO.deletePerson(p.getPersonID());
        personDAO.deletePerson(p2.getPersonID());
    }


    private void dropTable()   {
        log.debug("dropTable");
        String sql = "DROP TABLE persons";
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

    private void createTable()   {
        log.debug("createTable");
        String sql = "CREATE TABLE persons(PersonID int,lastName varchar(255),firstName varchar(255),address varchar(255),city varchar(255), PRIMARY KEY (PersonID))";
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

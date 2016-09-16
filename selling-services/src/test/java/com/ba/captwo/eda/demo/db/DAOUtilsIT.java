package com.ba.captwo.eda.demo.db;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * Created by u760245 on 04/07/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test_beans.xml"})
public class DAOUtilsIT {

    private final Logger log = LoggerFactory.getLogger(DAOUtilsIT.class);

    @Autowired
    DAOUtils daoUtils;
    @Autowired
    DataSource sellingDatasource;

    @Test
    public void testDatasource() throws Exception {
        log.info("sellingDatasource : " + sellingDatasource);
        Assert.assertNotNull(sellingDatasource);
    }

    @Test
    public void testConnect() throws Exception {

        Connection conn = sellingDatasource.getConnection();
        log.info("Connection : " + conn);
        System.out.println("Connection : " + conn);
        Assert.assertNotNull(conn);

        conn.close();

    }
    @Test
    public void testNextId() throws Exception {

        int numm = daoUtils.getNextId();
        log.info("testNextId : " + numm);
        System.out.println("testNextId : "+numm);
        Assert.assertTrue("id OK", numm > 0);


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
        //dropSequence();
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

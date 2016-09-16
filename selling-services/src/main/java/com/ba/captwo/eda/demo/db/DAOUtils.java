package com.ba.captwo.eda.demo.db;

import com.ba.captwo.eda.demo.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by u760245 on 04/07/2014.
 */
@Component
public class DAOUtils {

    private final Logger log = LoggerFactory.getLogger(DAOUtils.class);

    @Autowired
    private DataSource sellingDatasource;


    public int getNextId()  {

        log.debug("Datasource : "+ getSellingDatasource());
        ResultSet rs = null;
        Statement st = null;
        Connection conn = null;
        int nextInSeq = 0;
        try {
            conn = getSellingDatasource().getConnection();
            st = conn.createStatement();
            rs = st.executeQuery("select nextval('BA_SEQ')");
            while (rs.next()) {
                nextInSeq = rs.getInt(1);
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


        return nextInSeq;
    }

    /**
    private ArrayList<String> listTables()   {

        ArrayList<String> tables = new ArrayList<String>();
        String sql = "select * from SYS.SYSTABLES";
        log.debug("Datasource : "+ sellingDatasource);
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection conn = null;
        int nextInSeq = 0;
        try {
            conn = sellingDatasource.getConnection();
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next())  {
                String table = rs.getString("TABLENAME");
                log.debug(table);
                tables.add(table);
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
        return tables;
    }
     */

    public void truncateTable(String table)   {

        log.debug("TRANCATING : "+ table);

        String sql = "TRUNCATE "+table;
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
                if (st != null)st.close();
                if (conn != null)conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public DataSource getSellingDatasource() {
        return sellingDatasource;
    }

    public void setSellingDatasource(DataSource sellingDatasource) {
        this.sellingDatasource = sellingDatasource;
    }
}

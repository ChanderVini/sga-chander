/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.myappsecurity.sga.dao;

import com.myappsecurity.sga.util.DbConnection;
import com.myappsecurity.sga.vo.AdminVO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author csingh
 */
public class AdminDAO {
    private Logger logger = null;
    
    public AdminDAO () {
        logger = Logger.getLogger(AdminDAO.class);
    }
        
    public AdminVO[] fetchApplicationNames () throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();
            StringBuffer queryBuf = new StringBuffer ("SELECT * FROM application_tbl");
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            rs = stmt.executeQuery(queryBuf.toString ());
            queryBuf = null;
            ArrayList adminVOAL = new ArrayList (10);
            while (rs.next()) {
                AdminVO adminVO = new AdminVO ();
                String applicationname = rs.getString ("APP_NAME");
                String applicationType = rs.getString ("APP_TYPE_CD");
                String description = rs.getString ("DESCRIPTION");
                
                adminVO.setApplicationName(applicationname);
                adminVO.setApplicationType(applicationType);
                adminVO.setDescription(description);
                
                adminVOAL.add (adminVO);
            }
            DbConnection.close(connection, stmt, rs);
            AdminVO[] adminVOs = (AdminVO[]) adminVOAL.toArray(new AdminVO [adminVOAL.size()]);
            adminVOAL = null;
            return adminVOs;
        } finally {
            DbConnection.close (connection, stmt, null);
        }
    }
}

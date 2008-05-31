package com.myappsecurity.sga.dao;

import com.myappsecurity.sga.vo.UserVO;
import com.myappsecurity.sga.util.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author Chander Singh
 * @created.on Jan 16, 2008
 */
public class UserDAO {    
    private Logger logger = null;
            
    public UserDAO () {
        logger = Logger.getLogger (UserDAO.class);
    }
    
    public UserVO fetchUserProfile (UserVO userVO) throws Exception {        
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("SELECT * FROM user_tbl WHERE USER_NAME = '");
            queryBuf.append (userVO.getUserName());
            queryBuf.append ("' AND USER_TYPE_CD = '");
            queryBuf.append (userVO.getUserTypeCd());
            queryBuf.append ("' AND APP_NAME = '");
            queryBuf.append (userVO.getAppName());
            queryBuf.append ("' AND APP_TYPE_CD = '");
            queryBuf.append (userVO.getAppTypeCd());
            queryBuf.append ("'");
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(queryBuf.toString());
            queryBuf = null;
            while (rs.next ()) {
                String userTypeCd = rs.getString (1);
                String username = rs.getString (2);
                String userpass = rs.getString (3);
                String firstName = rs.getString (4);
                String lastName = rs.getString (5);
                String address1 = rs.getString (6);
                String address2 = rs.getString (7);
                String city = rs.getString (8);
                String state = rs.getString (9);
                String country = rs.getString (10);
                String zipcode = rs.getString (11);
                String offPhone = rs.getString (12);
                String homePhone1 = rs.getString (13);
                String homePhone2 = rs.getString (14);
                String mobile = rs.getString (15);
                String fax = rs.getString (16);
                String email1 = rs.getString (17);
                String email2 = rs.getString (18);
                String appTypeCd = rs.getString(19);
                String appName = rs.getString (20);
                String secretQues = rs.getString (21);
                String secretAns = rs.getString (22);

                userVO = new UserVO ();
                userVO.setAppTypeCd(appTypeCd);
                userVO.setAppName(appName);
                userVO.setUserName(username);
                userVO.setUserTypeCd(userTypeCd);
                userVO.setUserPassword(userpass);
                userVO.setFirstName(firstName);
                userVO.setLastName(lastName);
                userVO.setAddress1(address1);
                userVO.setAddress2(address2);
                userVO.setCity(city);
                userVO.setState(state);
                userVO.setCountry(country);
                userVO.setZipCode(zipcode);
                userVO.setOffPhone(offPhone);
                userVO.setHomePhone1(homePhone1);
                userVO.setHomePhone2(homePhone2);
                userVO.setMobile(mobile);
                userVO.setFax(fax);
                userVO.setEmail1(email1);
                userVO.setEmail2(email2);
                userVO.setSecretQues(secretQues);
                userVO.setSecretAns(secretAns);
            }
             return userVO;
        } finally {
            DbConnection.close(connection, stmt, rs);
        }
    }
    
   /**
    * 
    * @param username
    * @param password
    * @param userTypeCd
    * @param appTypeCd
    * @param appName
    * @return
    * @throws java.lang.Exception
    */
    public UserVO authenticate (String username, String password, String userTypeCd, String appTypeCd, String appName) 
            throws Exception {
        StringBuffer queryBuf = new StringBuffer ("SELECT APP_TYPE_CD ATC, APP_NAME AN, USER_TYPE_CD UTC, USER_NAME UN, " +
                "USER_PASSWORD UP, FIRST_NAME FN, LAST_NAME LN, ADDRESS1 ADDR1, ADDRESS2 ADDR2, CITY CT, STATE ST, " +
                "COUNTRY CNTR, ZIP_CODE ZIP, OFFPHONE OFF, HOMEPHONE1 HP1, HOMEPHONE2 HP2, MOBILE MOB, FAX FX, " +
                "EMAIL1 EM1, EMAIL2 EM2, SECRET_QUES SQ,  SECRET_ANS SA FROM user_tbl WHERE APP_TYPE_CD = '");
        queryBuf.append (appTypeCd);
        queryBuf.append ("' AND APP_NAME = '");
        queryBuf.append (appName);
        queryBuf.append ("' AND USER_NAME  = '");
        queryBuf.append (username);
        queryBuf.append ("' AND USER_PASSWORD = '");
        queryBuf.append (password);
        queryBuf.append ("' AND USER_TYPE_CD = '");
        queryBuf.append (userTypeCd);
        queryBuf.append ("'");
        MDC.remove ("MyMDC6");
        MDC.put("MyMDC6", this.getClass().getName());
        logger.debug (queryBuf.toString ());
        MDC.remove("MyMDC6");
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        UserVO userVO = null;
        try {
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();        
            rs = stmt.executeQuery (queryBuf.toString ());
            queryBuf = null;        
            if (rs.next()) {
                String apptypeCd = rs.getString ("ATC");
                String appname = rs.getString ("AN");
                String userName = rs.getString("UN");
                String usertypeCd = rs.getString ("UTC");
                String userPass = rs.getString ("UP");
                String firstName = rs.getString ("FN");
                String lastName = rs.getString ("LN");
                String address1 = rs.getString ("ADDR1");
                String address2 = rs.getString ("ADDR2");
                String city = rs.getString ("CT");
                String state = rs.getString ("ST");
                String country = rs.getString ("CNTR");
                String zipcode = rs.getString ("ZIP");
                String offPhone = rs.getString ("OFF");
                String homePhone1 = rs.getString ("HP1");
                String homePhone2 = rs.getString ("HP2");
                String mobile = rs.getString ("MOB");
                String fax = rs.getString ("FX");
                String email1 = rs.getString ("EM1");
                String email2 = rs.getString ("EM2");
                String secretQues = rs.getString ("SQ");
                String secretAns = rs.getString ("SA");

                userVO = new UserVO ();
                userVO.setAppTypeCd(apptypeCd);
                userVO.setAppName(appname);
                userVO.setUserName(userName);
                userVO.setUserTypeCd(usertypeCd);
                userVO.setUserPassword(userPass);
                userVO.setFirstName(firstName);
                userVO.setLastName(lastName);
                userVO.setAddress1(address1);
                userVO.setAddress2(address2);
                userVO.setCity(city);
                userVO.setState(state);
                userVO.setCountry(country);
                userVO.setZipCode(zipcode);
                userVO.setOffPhone(offPhone);
                userVO.setHomePhone1(homePhone1);
                userVO.setHomePhone2(homePhone2);
                userVO.setMobile(mobile);
                userVO.setFax(fax);
                userVO.setEmail1(email1);
                userVO.setEmail2(email2);
                userVO.setSecretQues(secretQues);
                userVO.setSecretAns(secretAns);
            }
        } finally {
            DbConnection.close(connection, stmt, rs);
        }
        return userVO;
    }
    
    /**
     * 
     * @param userVO
     * @throws java.lang.Exception
     */
    public void insertUser (UserVO userVO) throws Exception {        
        StringBuffer queryBuf = new StringBuffer ("INSERT INTO user_tbl (APP_TYPE_CD, APP_NAME, USER_TYPE_CD, USER_NAME, " +
                "USER_PASSWORD, FIRST_NAME, LAST_NAME, ADDRESS1, ADDRESS2, CITY, STATE, COUNTRY, ZIP_CODE, OFFPHONE, " +
                "HOMEPHONE1, HOMEPHONE2, MOBILE, FAX, EMAIL1, EMAIL2, SECRET_QUES, SECRET_ANS) VALUES ('");
        queryBuf.append (userVO.getAppTypeCd());
        queryBuf.append ("', '");
        queryBuf.append(userVO.getAppName());
        queryBuf.append("', '");
        queryBuf.append(userVO.getUserTypeCd());
        queryBuf.append("', '");
        queryBuf.append(userVO.getUserName());
        queryBuf.append("', '");
        queryBuf.append(userVO.getUserPassword());
        queryBuf.append("', '");
        queryBuf.append(userVO.getFirstName());
        queryBuf.append("', '");
        queryBuf.append(userVO.getLastName());
        queryBuf.append("', '"); 
        queryBuf.append(userVO.getAddress1());
        queryBuf.append("', '");
        queryBuf.append(userVO.getAddress2());
        queryBuf.append("', '");
        queryBuf.append(userVO.getCity());
        queryBuf.append("', '");
        queryBuf.append(userVO.getState());
        queryBuf.append("', '");
        queryBuf.append(userVO.getCountry());
        queryBuf.append("', '");
        queryBuf.append(userVO.getZipCode());
        queryBuf.append("', '");
        queryBuf.append(userVO.getOffPhone());
        queryBuf.append("', '");
        queryBuf.append(userVO.getHomePhone1());
        queryBuf.append("', '");
        queryBuf.append(userVO.getHomePhone2());
        queryBuf.append("', '");
        queryBuf.append(userVO.getMobile());
        queryBuf.append("', '");
        queryBuf.append(userVO.getFax());
        queryBuf.append("', '");
        queryBuf.append(userVO.getEmail1());
        queryBuf.append("', '");
        queryBuf.append(userVO.getEmail2());
        queryBuf.append("', '");
        queryBuf.append(userVO.getSecretQues());
        queryBuf.append("', '");
        queryBuf.append(userVO.getSecretAns());
        queryBuf.append("')");

        MDC.remove ("MyMDC6");
        MDC.put("MyMDC6", this.getClass().getName());
        logger.debug (queryBuf.toString ());
        MDC.remove("MyMDC6");
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DbConnection.getConnection  ();
            stmt = connection.createStatement();                
            stmt.executeUpdate(queryBuf.toString());
            queryBuf = null;
        } finally {
            DbConnection.close(connection, stmt, null);    
        }        
    }
    
    /**
     * 
     * @param userVO
     * @throws java.lang.Exception
     */
    public void resetPassword (UserVO userVO) throws Exception {
        StringBuffer queryBuf = new StringBuffer ("UPDATE user_tbl SET USER_PASSWORD = '");
        queryBuf.append (userVO.getUserPassword());
        queryBuf.append ("' WHERE USER_TYPE_CD = '");
        queryBuf.append (userVO.getUserTypeCd());
        queryBuf.append ("' AND APP_TYPE_CD = '");
        queryBuf.append (userVO.getAppTypeCd());
        queryBuf.append ("' AND APP_NAME = '");
        queryBuf.append (userVO.getAppName());
        queryBuf.append ("' AND USER_NAME = '");
        queryBuf.append (userVO.getUserName());
        queryBuf.append ("'");
        
        MDC.remove ("MyMDC6");
        MDC.put("MyMDC6", this.getClass().getName());
        logger.debug (queryBuf.toString ());
        MDC.remove("MyMDC6");
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DbConnection.getConnection  ();
            stmt = connection.createStatement();                
            stmt.executeUpdate(queryBuf.toString());
            queryBuf = null;
        } finally {
            DbConnection.close(connection, stmt, null);    
        }               
    }
    
    /**
     * 
     * @param userVO
     * @return
     * @throws java.lang.Exception
     */
    public UserVO updateUser (UserVO userVO) throws Exception {
        StringBuffer queryBuf = new StringBuffer ("UPDATE user_tbl SET USER_PASSWORD = '");
        queryBuf.append (userVO.getUserPassword());
        queryBuf.append ("', FIRST_NAME = '");
        queryBuf.append (userVO.getFirstName());
        queryBuf.append ("', LAST_NAME = '");
        queryBuf.append (userVO.getLastName());
        queryBuf.append ("', ADDRESS1 = '");
        queryBuf.append (userVO.getAddress1());
        queryBuf.append ("', ADDRESS2 = '");
        queryBuf.append (userVO.getAddress2());
        queryBuf.append ("', CITY = '");
        queryBuf.append (userVO.getCity());
        queryBuf.append ("', STATE = '");
        queryBuf.append (userVO.getState());
        queryBuf.append ("', COUNTRY = '");
        queryBuf.append (userVO.getCountry());
        queryBuf.append ("', ZIP_CODE = '");
        queryBuf.append (userVO.getZipCode());
        queryBuf.append ("', OFFPHONE = '");
        queryBuf.append (userVO.getOffPhone());
        queryBuf.append ("', HOMEPHONE1 = '");
        queryBuf.append (userVO.getHomePhone1());
        queryBuf.append ("', HOMEPHONE2 = '");
        queryBuf.append (userVO.getHomePhone2());
        queryBuf.append ("', MOBILE = '");
        queryBuf.append (userVO.getMobile());
        queryBuf.append ("', FAX = '");
        queryBuf.append (userVO.getFax());
        queryBuf.append ("', EMAIL1 = '");
        queryBuf.append (userVO.getEmail1());
        queryBuf.append ("', EMAIL2 = '");
        queryBuf.append (userVO.getEmail2());
        queryBuf.append ("', SECRET_QUES = '");
        queryBuf.append (userVO.getSecretQues());
        queryBuf.append ("', SECRET_ANS = '");
        queryBuf.append (userVO.getSecretAns());
        queryBuf.append ("' WHERE USER_TYPE_CD = '");
        queryBuf.append (userVO.getUserTypeCd());
        queryBuf.append ("' AND USER_NAME = '");
        queryBuf.append (userVO.getUserName());
        queryBuf.append ("' AND APP_TYPE_CD = '");
        queryBuf.append (userVO.getAppTypeCd ());
        queryBuf.append ("' AND APP_NAME = '");
        queryBuf.append (userVO.getAppName ());
        queryBuf.append ("'");
        MDC.remove ("MyMDC6");
        MDC.put("MyMDC6", this.getClass().getName());
        logger.debug (queryBuf.toString ());
        MDC.remove("MyMDC6");
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(queryBuf.toString());
            queryBuf = null;
        } finally {
            DbConnection.close(connection, stmt, null);        
        }
        return authenticate(userVO.getUserName(), userVO.getUserPassword(), userVO.getUserTypeCd(), userVO.getAppTypeCd(), userVO.getAppName());
    }
    
    /**
     * 
     * @param userVO
     * @return
     * @throws java.lang.Exception
     */
    public UserVO validateUser (UserVO userVO) throws Exception {
        StringBuffer queryBuf = new StringBuffer ("SELECT SECRET_QUES FROM user_tbl WHERE USER_NAME = '");
        queryBuf.append (userVO.getUserName());
        queryBuf.append ("' AND USER_TYPE_CD = '");
        queryBuf.append  (userVO.getUserTypeCd());
        queryBuf.append ("' AND APP_TYPE_CD = '");
        queryBuf.append (userVO.getAppTypeCd());
        queryBuf.append ("' AND APP_NAME = '");
        queryBuf.append (userVO.getAppName());
        queryBuf.append ("' AND (EMAIL1 = '");
        queryBuf.append (userVO.getEmail1());
        queryBuf.append ("' OR EMAIL2 = '");
        queryBuf.append (userVO.getEmail1());
        queryBuf.append ("')");
        
        MDC.remove ("MyMDC6");
        MDC.put("MyMDC6", this.getClass().getName());
        logger.debug (queryBuf.toString ());
        MDC.remove("MyMDC6");
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(queryBuf.toString());
            queryBuf = null;
            if (rs.next ()) {
                String secretQues = rs.getString ("SECRET_QUES");
                userVO.setSecretQues(secretQues);
            }
            DbConnection.close(connection, stmt, rs);
            return userVO;
        } finally {
            DbConnection.close(connection, stmt, rs);        
        }
    }
    
    /**
     * 
     * @param userVO
     * @return
     * @throws java.lang.Exception
     */
     public boolean validateUserSecret (UserVO userVO) throws Exception {
        boolean isValid = false;
        StringBuffer queryBuf = new StringBuffer ("SELECT COUNT(1) FROM user_tbl WHERE USER_NAME = '");
        queryBuf.append (userVO.getUserName());
        queryBuf.append ("' AND USER_TYPE_CD = '");
        queryBuf.append  (userVO.getUserTypeCd());
        queryBuf.append ("' AND APP_TYPE_CD = '");
        queryBuf.append (userVO.getAppTypeCd());
        queryBuf.append ("' AND APP_NAME = '");
        queryBuf.append (userVO.getAppName());
        queryBuf.append ("' AND SECRET_QUES = '");
        queryBuf.append (userVO.getSecretQues());
        queryBuf.append ("' AND SECRET_ANS = '");
        queryBuf.append (userVO.getSecretAns());
        queryBuf.append ("'");
        
        MDC.remove ("MyMDC6");
        MDC.put("MyMDC6", this.getClass().getName());
        logger.debug (queryBuf.toString ());
        MDC.remove("MyMDC6");
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(queryBuf.toString());
            queryBuf = null;
            if (rs.next ()) {
                int count = rs.getInt (1);
                if (count > 0) {
                    isValid = true;
                }
            }
            DbConnection.close(connection, stmt, rs);
            return isValid;
        } finally {
            DbConnection.close(connection, stmt, rs);        
        }
    }
}

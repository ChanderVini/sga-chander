package com.myappsecurity.sga.dao;

import com.myappsecurity.sga.vo.CategoryVO;
import com.myappsecurity.sga.util.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author Chander Singh
 * @created.on Jan 17, 2008
 */
public class CategoryDAO {          
    private Logger logger = null;
    
    /**
     * Creates a new instance of CategoryDAO with parameter as IP Address and User ID from where the request had send the request.
     */
    public CategoryDAO() {
        logger = Logger.getLogger(CategoryDAO.class);
    }
    
    /**
     * Method retreiving all Categories details available in database.
     * @throws java.lang.Exception Exception thrown when either Connection Object with Database is not available, 
     * when any error occurs while fetching data from database or no data is found in the database.
     * @return Array of all available categories from database as per search criteria.
     */
    public CategoryVO[] fetchCategories () throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = DbConnection.getConnection();
            StringBuffer queryBuf = new StringBuffer ("SELECT CATEGORY_ID, APP_TYPE_CD, APP_NAME, CATEGORY_CD, CATEGORY_NAME, DESCRIPTION FROM category_tbl");
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            stmt = connection.createStatement();
            rs = stmt.executeQuery(queryBuf.toString());
            queryBuf = null;
            CategoryVO[] categoryVOs = populate (rs);
            DbConnection.close(connection, stmt, rs);
            return categoryVOs;
        } finally {
            DbConnection.close (connection, stmt, rs);
        }
    }
    
    /**
     * 
     * @param categoryIds
     * @throws java.lang.Exception
     */
    public void deleteCategories (long[] categoryIds) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();
            for (int cnt = 0; cnt < categoryIds.length; cnt++) {
                StringBuffer queryBuf = new StringBuffer ("DELETE FROM category_tbl WHERE CATEGORY_ID = '");
                queryBuf.append(categoryIds[cnt]);
                MDC.remove ("MyMDC6");
                MDC.put("MyMDC6", this.getClass().getName());
                logger.debug (queryBuf.toString ());
                MDC.remove("MyMDC6");
                stmt.addBatch(queryBuf.toString());
                queryBuf = null;
            }
            stmt.executeBatch();
        } finally {
            DbConnection.close(connection, stmt, null);
        }
    }
    
    /**
     * Helper method for extracting data from ResultSet object and populating CategoryVO object(s).
     * @param rs ResultSet object from which data is to be retreived.
     * @throws java.sql.SQLException Exception thrown if any error occurs while fetching data from ResultSet.
     * @return Array of CategoryVO as per data extracted from ResultSet.
     */
    private CategoryVO[] populate (ResultSet rs) throws SQLException {
        ArrayList categoryVOAL = new ArrayList (10);
        while (rs.next()) {
            long categoryId = rs.getLong (1);
            String appTypeCd = rs.getString (2);
            String appName = rs.getString (3);
            String categoryCd = rs.getString(4);
            String categoryName = rs.getString(5);
            String description = rs.getString(6);
            CategoryVO categoryVO = new CategoryVO();
            categoryVO.setCategoryId(categoryId);
            categoryVO.setAppTypeCd(appTypeCd);
            categoryVO.setAppName(appName);
            categoryVO.setCategoryCd(categoryCd);
            categoryVO.setCategoryName(categoryName);
            categoryVO.setDescription(description);
            categoryVOAL.add(categoryVO);
        }
        CategoryVO[] categoryVOs = (CategoryVO[]) categoryVOAL.toArray(new CategoryVO[categoryVOAL.size()]);
        categoryVOAL = null;
        return categoryVOs;
    }
}

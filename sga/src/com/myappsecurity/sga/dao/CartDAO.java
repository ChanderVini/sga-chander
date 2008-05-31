package jcom.myappsecurity.sga.dao;

import com.myappsecurity.sga.vo.ShoppingCartVO;
import com.myappsecurity.sga.util.DbConnection;

import com.myappsecurity.sga.vo.AddressVO;
import com.myappsecurity.sga.vo.CartVO;
import com.myappsecurity.sga.vo.CreditCardVO;
import com.myappsecurity.sga.vo.UserVO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author Chander Singh
 * @created.on Jan 18, 2008
 */
public class CartDAO {
    private Logger logger = null;
    
    /**
     * 
     */
    public CartDAO () {
        logger = Logger.getLogger(CartDAO.class);
    }
    
    /**
     * 
     * @param shoppingCartVO
     * @throws java.lang.Exception
     */
    public void updateCart (ShoppingCartVO shoppingCartVO) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();
            StringBuffer queryBuf = new StringBuffer ("DELETE FROM cart_tbl WHERE CART_ID = ");
            queryBuf.append (shoppingCartVO.getCartId());
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            stmt.executeUpdate(queryBuf.toString());
            queryBuf = null;                
            DbConnection.close (null, stmt, null);
            createCart (shoppingCartVO);
            if ("Y".equals(shoppingCartVO.getCompleted())) {
                AddressVO[] addressVOs = shoppingCartVO.getAddressVOs();
                insertAddresses (connection, addressVOs);
                CreditCardVO creditCardVO = shoppingCartVO.getCreditCardVO();
                insertCreditCardInfo(connection, creditCardVO, shoppingCartVO.getUserName(), shoppingCartVO.getUserTypeCd(), shoppingCartVO.getAppTypeCd(), shoppingCartVO.getAppName());
            }
        } finally {
            DbConnection.close (connection, stmt, null);
        }
    }
    
    /**
     * 
     * @param shoppingCartVO
     * @throws java.lang.Exception
     */
    public void createCart (ShoppingCartVO shoppingCartVO) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();
            CartVO[] cartVOs = shoppingCartVO.getCartVOs();
            Date currdate = new Date (Calendar.getInstance().getTimeInMillis());
            for (int cnt = 0; cnt < cartVOs.length; cnt++) {
                StringBuffer queryBuf = new StringBuffer ("INSERT INTO cart_tbl (CART_ID, APP_TYPE_CD, APP_NAME, USER_TYPE_CD, " +
                        "USER_NAME, PRODUCT_ID, PRODUCT_PRICE, QTY, CREATE_DT, COMPLETED) VALUES (");
                queryBuf.append (shoppingCartVO.getCartId());
                queryBuf.append (", '");
                queryBuf.append (shoppingCartVO.getAppTypeCd());
                queryBuf.append ("', '");
                queryBuf.append (shoppingCartVO.getAppName());
                queryBuf.append ("', '");
                queryBuf.append (shoppingCartVO.getUserTypeCd());
                queryBuf.append ("', '");
                queryBuf.append (shoppingCartVO.getUserName());
                queryBuf.append ("', ");
                queryBuf.append (cartVOs[cnt].getProductId());
                queryBuf.append (", ");
                queryBuf.append (cartVOs[cnt].getProductPrice());
                queryBuf.append (", ");
                queryBuf.append (cartVOs[cnt].getQuantity());
                queryBuf.append (", '");
                queryBuf.append (currdate.toString());
                queryBuf.append ("', '");
                queryBuf.append (shoppingCartVO.getCompleted());
                queryBuf.append ("')");
                MDC.remove ("MyMDC6");
                MDC.put("MyMDC6", this.getClass().getName());
                logger.debug (queryBuf.toString ());
                MDC.remove("MyMDC6");
                stmt.addBatch (queryBuf.toString());
                queryBuf = null;                
            }
            stmt.executeBatch();            
            DbConnection.close (connection, stmt, null);
           
        } finally {
            DbConnection.close (connection, stmt, null);
        }
    }
    
    /**
     * 
     * @param connection
     * @param addressVOs
     * @throws java.lang.Exception
     */
    private void insertAddresses (Connection connection, AddressVO[] addressVOs) throws Exception {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            Date currdate = new Date (Calendar.getInstance().getTimeInMillis());
            for (int cnt = 0; cnt < addressVOs.length; cnt++) {
                StringBuffer queryBuf = new StringBuffer ("INSERT INTO address_tbl (CART_ID, ADDRESS_TYPE, FIRST_NAME, " +
                        "LAST_NAME, ADDRESS1, ADDRESS2, CITY, STATE, COUNTRY, ZIPCODE, OFFPHONE, HOMEPHONE1, HOMEPHONE2, " +
                        "MOBILE, FAX, EMAIL1, EMAIL2, ADDRESS_DT) VALUES (");
                queryBuf.append (addressVOs[cnt].getCartId());
                queryBuf.append (", '");
                queryBuf.append (addressVOs[cnt].getAddressType());
                queryBuf.append ("', '");
                queryBuf.append (addressVOs[cnt].getFirstName());
                queryBuf.append ("', '");
                queryBuf.append (addressVOs[cnt].getLastName());
                queryBuf.append ("', '");
                queryBuf.append (addressVOs[cnt].getAddress1());
                queryBuf.append ("', '");
                queryBuf.append (addressVOs[cnt].getAddress2());
                queryBuf.append ("', '");
                queryBuf.append (addressVOs[cnt].getCity());
                queryBuf.append ("', '");
                queryBuf.append (addressVOs[cnt].getState());
                queryBuf.append ("', '");
                queryBuf.append (addressVOs[cnt].getCountry());
                queryBuf.append ("', '");
                queryBuf.append (addressVOs[cnt].getZipCode());
                queryBuf.append ("', '");
                queryBuf.append (addressVOs[cnt].getOffPhone());
                queryBuf.append ("', '"); 
                queryBuf.append (addressVOs[cnt].getHomePhone1());
                queryBuf.append ("', '");
                queryBuf.append (addressVOs[cnt].getHomePhone2());
                queryBuf.append ("', '");
                queryBuf.append (addressVOs[cnt].getMobile());
                queryBuf.append ("', '");
                queryBuf.append (addressVOs[cnt].getFax());
                queryBuf.append ("', '");
                queryBuf.append (addressVOs[cnt].getEmail1());
                queryBuf.append ("', '");
                queryBuf.append (addressVOs[cnt].getEmail2());
                queryBuf.append ("', '");
                queryBuf.append (currdate.toString());
                queryBuf.append ("')");
                MDC.remove ("MyMDC6");
                MDC.put("MyMDC6", this.getClass().getName());
                logger.debug (queryBuf.toString ());
                MDC.remove("MyMDC6");
                stmt.addBatch(queryBuf.toString());
                queryBuf = null;
            }
            stmt.executeBatch();
            DbConnection.close(null, stmt, null);
       } finally {
            DbConnection.close (null, stmt, null);
        }
    }
    
    /**
     * 
     * @param connection
     * @param creditCardVO
     * @throws java.lang.Exception
     */
    public void insertCreditCardInfo (Connection connection, CreditCardVO creditCardVO, String username, String userTypeCd, String apptype, String appname) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            
            StringBuffer queryBuf = new StringBuffer ("SELECT COUNT(1) FROM credit_card_tbl WHERE USER_NAME = '");
            queryBuf.append (username);
            queryBuf.append ("' AND USER_TYPE_CD = '");
            queryBuf.append (userTypeCd);
            queryBuf.append ("' AND APP_TYPE_CD = '");
            queryBuf.append (apptype);
            queryBuf.append ("' AND APP_NAME = '");
            queryBuf.append (appname);
            queryBuf.append ("'");
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");            
            rs = stmt.executeQuery(queryBuf.toString());
            queryBuf = null;
            
            boolean exists = false;
            if (rs.next ()) {
                if (rs.getInt(1) > 0) {
                    exists = true;
                }
            }
            if (exists) {
                DbConnection.close(null, stmt, rs);
                return;
            }
            queryBuf = new StringBuffer ("INSERT INTO credit_card_tbl (USER_NAME, USER_TYPE_CD, APP_TYPE_CD, APP_NAME, CART_ID, CARD_TYPE, CARD_NBR, CVV_NBR, EXP_MON, EXP_YEAR, " +
                    "CARD_USER_NAME) VALUES ('");
            queryBuf.append (username);
            queryBuf.append ("','");
            queryBuf.append (userTypeCd);
            queryBuf.append ("','");
            queryBuf.append (apptype);            
            queryBuf.append ("','");
            queryBuf.append (appname);            
            queryBuf.append ("',");
            queryBuf.append (creditCardVO.getCartId ());
            queryBuf.append (",'");
            queryBuf.append (creditCardVO.getCcType());
            queryBuf.append ("','");
            queryBuf.append (creditCardVO.getCcNbr());
            queryBuf.append ("',");
            queryBuf.append (creditCardVO.getCvvNbr());
            queryBuf.append (",");
            queryBuf.append (creditCardVO.getExpMon());
            queryBuf.append (",");
            queryBuf.append (creditCardVO.getExpYear());
            queryBuf.append (",'");
            queryBuf.append (creditCardVO.getCardUserName());
            queryBuf.append ("')");
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            stmt.execute(queryBuf.toString ());
            queryBuf = null;
            DbConnection.close(null, stmt, rs);
       } finally {
            DbConnection.close (null, stmt, rs);
        }
    }
    
    /**
     * 
     * @param appTypeCd
     * @param appName
     * @param userTypeCd
     * @param userName
     * @param createDt
     * @return
     * @throws java.lang.Exception
     */
    public ShoppingCartVO fetchCurrentCart (String appTypeCd, String appName, String userTypeCd, String userName) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        ShoppingCartVO shoppingCartVO = new ShoppingCartVO();
        try {
            StringBuffer queryBuf = new StringBuffer ("SELECT CART.*, PROD.* FROM cart_tbl CART, product_tbl PROD WHERE APP_TYPE_CD = '");
            queryBuf.append (appTypeCd);
            queryBuf.append ("' AND APP_NAME = '");
            queryBuf.append(appName);
            queryBuf.append ("' AND USER_TYPE_CD = '");
            queryBuf.append (userTypeCd);
            queryBuf.append ("' AND USER_NAME = '");
            queryBuf.append (userName);
            queryBuf.append ("' AND COMPLETED = 'N' AND CART.PRODUCT_ID = PROD.PRODUCT_ID");
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();
            
            rs = stmt.executeQuery(queryBuf.toString());
            queryBuf = null;
            
            shoppingCartVO.setAppName(appName);
            shoppingCartVO.setAppTypeCd(appTypeCd);
            shoppingCartVO.setUserTypeCd(userTypeCd);
            shoppingCartVO.setUserName(userName);
                
            while (rs.next ()) {
                String cartid = rs.getString (1);
                appTypeCd = rs.getString (2);
                appName = rs.getString (3);
                String productid = rs.getString (4);
                userTypeCd = rs.getString (5);
                userName = rs.getString (6);
                String qty = rs.getString (7);
                String productprice = rs.getString (8);
                String createdt = rs.getString (9);
                String completed = rs.getString (10);                
                String productName = rs.getString (14);
                                
                Long cartId = Long.parseLong (cartid);
                shoppingCartVO.setCartId(cartId);
                shoppingCartVO.setCompleted(completed);
                shoppingCartVO.setUserName(userName);
                shoppingCartVO.setUserTypeCd(userTypeCd);
                shoppingCartVO.setAppName(appName);
                shoppingCartVO.setAppTypeCd(appTypeCd);
                
                CartVO cartVO = new CartVO ();                
                long productId = Long.parseLong(productid);
                cartVO.setProductId(productId);
                
                double productPrice = Double.parseDouble(productprice); 
                cartVO.setProductPrice(productPrice);
                
                int quantity = Integer.parseInt (qty);
                cartVO.setQuantity(quantity);
                
                cartVO.setProductName(productName);
                shoppingCartVO.addCart (cartVO);
            }
            DbConnection.close(connection, stmt, rs);
            return shoppingCartVO;
        } finally {
            DbConnection.close(connection, stmt, rs);
        }
    }
    
    public CreditCardVO fetchCreditCard (UserVO userVO) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("SELECT * FROM credit_card_tbl WHERE USER_NAME = '");
            queryBuf.append (userVO.getUserName());
            queryBuf.append ("' AND USER_TYPE_CD = '");
            queryBuf.append (userVO.getUserTypeCd());
            queryBuf.append ("' AND APP_TYPE_CD = '");
            queryBuf.append (userVO.getAppTypeCd());
            queryBuf.append ("' AND APP_NAME = '");
            queryBuf.append (userVO.getAppName());
            queryBuf.append ("'");
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(queryBuf.toString());
            
            CreditCardVO creditCardVO = new CreditCardVO ();
            if (rs.next()) {
                String username = rs.getString (1);
                String userTypeCd = rs.getString (2);
                String appTypeCd = rs.getString (3);
                String appName = rs.getString (4);
                String cardType = rs.getString (5);
                String cardNbr = rs.getString (6);
                String cvvNbr = rs.getString (7);
                String expMon = rs.getString (8);
                String expYear = rs.getString (9);
                String cardUserName = rs.getString (10);
                
                creditCardVO.setUserTypeCd(userTypeCd);
                creditCardVO.setUsername(username);
                creditCardVO.setAppName(appName);
                creditCardVO.setAppTypeCd(appTypeCd);
                creditCardVO.setCardUserName(cardUserName);
                creditCardVO.setCcType(cardType);
                creditCardVO.setCcNbr(cardNbr);
                creditCardVO.setCvvNbr(cvvNbr);
                creditCardVO.setExpMon(expMon);
                creditCardVO.setExpYear(expYear);
            }
            DbConnection.close(connection, stmt, rs);
            return creditCardVO;
        } finally {
            DbConnection.close(connection, stmt, rs);
        }
    }
}

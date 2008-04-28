package com.myappsecurity.sga.util;

/**
 *
 * @author Chander Singh
 * @created.on Jan 19, 2008
 */
public class ApplicationUtil {
    /**
     * 
     * @param appTypeCd
     * @param appName
     * @param operation
     * @return
     */
    public String getRedirectPage (String appTypeCd, String appName, String operation) {
        String redirectURL = "login";
        if ("ecom".equals (appTypeCd)) {
            return  fetchRedirect (appTypeCd, appName, operation);
        }
        
        if ("admin".equals (appTypeCd)) {
            return fetchRedirect (appTypeCd, operation);
        }
        return redirectURL;
    }    
    
    private String fetchRedirect (String appTypeCd, String operation) {
        if ("console".equals(operation)) {
            return appTypeCd + "/console.jsp";
        }
        if ("appfilter".equals(operation)) {
            return appTypeCd + "/console.jsp";
        }
        return "";
    }
    /**
     * 
     * @param appTypeCd
     * @param appName
     * @param operation
     * @return
     */
    private String fetchRedirect (String appTypeCd, String appName, String operation) {
        if ("addproduct".equals(operation)) {
            return appTypeCd + "/addproduct.jsp";
        }
        if ("addreview".equals (operation)) {
            return appTypeCd + "/productdesc.jsp";
        }
        if ("addreviewerror".equals (operation)) {
            return appTypeCd + "/productdesc.jsp";
        }
        if ("addcart".equals(operation) || "cart".equals (operation) || "updcart".equals (operation)) {
            return appTypeCd + "/cart.jsp";
        }
        if ("authenticate".equals (operation )) {
            return appTypeCd + "-" + appName + "/category";
        }        
        if ("category".equals (operation )) {
            return appTypeCd + "/category.jsp";
        }
        if ("changepass".equals (operation)) {
            return appTypeCd + "/changepass.jsp";
        }
        if ("changepasssub".equals (operation)) {
            return appTypeCd + "-" + appName + "/category";
        }
        if ("checkouterror".equals (operation)) {
            return appTypeCd + "/shipdet1.jsp";
        }
        if ("checkout1".equals(operation)) {
            return appTypeCd + "/shipdet1.jsp";
        }
        if ("checkout1error".equals(operation)) {
            return appTypeCd + "/shipdet1.jsp";
        }
        if ("checkout".equals (operation)) {
            return appTypeCd + "/category.jsp";
        }
        if ("contact".equals(operation)) {
            return appTypeCd + "/contactus.jsp";
        }
        if ("contactsub".equals(operation)) {
            return appTypeCd + "/contactus.jsp";
        }
        if ("contactsuberror".equals(operation)) {
            return appTypeCd + "/contactus.jsp";
        }
        if ("delwishlist".equals(operation)) {
            return appTypeCd + "/listwishlist.jsp";
        }
        if ("exception".equals(operation)){
            return appTypeCd + "/exception.jsp";
        }
        if ("forgotpass".equals (operation)) {
            return appTypeCd + "/forgotpass.jsp";
        }
        if ("forgotpasserror".equals (operation)) {
            return appTypeCd + "/forgotpass.jsp";
        }
        if ("forgotpasssub".equals (operation)) {
            return appTypeCd + "/resetpass.jsp";
        }
        if ("listwishlist".equals(operation)) {
            return appTypeCd + "/listwishlist.jsp";
        }
        if ("login".equals(operation)) {
            return appTypeCd + "/login.jsp";
        }
        if ("loginerror".equals(operation)) {
            return appTypeCd + "/login.jsp";
        }
        if ("logout".equals(operation)) {
            return appTypeCd + "/logout_temp.html";
        }
        if ("profile".equals (operation)) {
            return appTypeCd + "/profile.jsp";
        }
        if ("product".equals (operation)) {
            return appTypeCd + "/product.jsp";
        }
        if ("productdesc".equals (operation)) {
            return appTypeCd + "/productdesc.jsp";
        }
        if ("producterror".equals (operation)) {
            return appTypeCd + "/addproduct.jsp";
        }
        if ("productsub".equals (operation)) {
            return appTypeCd + "/category.jsp";
        }
        if ("profileerror".equals (operation)) {
            return appTypeCd + "/profile.jsp";
        }
        if ("register".equals(operation)) {
            return appTypeCd + "/register.jsp";
        }
        if ("registererror".equals(operation)) {
            return appTypeCd + "/register.jsp";
        }
        if ("registersub".equals(operation)) {
            return appTypeCd + "/category.jsp";
        }
        if ("resetpass".equals(operation)) {
            return appTypeCd + "/changepass.jsp";
        }
        if ("resetpasserror".equals(operation)) {
            return appTypeCd + "/resetpass.jsp";
        }
        if ("resetpasssub".equals(operation)) {
            return appTypeCd + "/login.jsp";
        }
        if ("resetpasssuberror".equals(operation)) {
            return appTypeCd + "/changepass.jsp";
        }
        if ("savecart".equals(operation)) {
            return appTypeCd + "/cart.jsp";
        }
        if ("search".equals (operation)) {
            return appTypeCd + "/search.jsp";
        }
        if ("searcherror".equals (operation)) {
            return appTypeCd + "/search.jsp";
        }
        if ("searchsub".equals (operation)) {
            return appTypeCd + "/searchresult.jsp";
        }
        if ("shipdet".equals(operation)) {
            return appTypeCd + "/shipdet.jsp";
        }
        if ("updateprofile".equals (operation)) {
            return appTypeCd + "/category.jsp";
        }
        if ("updateproduct".equals (operation)) {
            return appTypeCd + "/updateproduct.jsp";
        }
        if ("updateproductsub".equals (operation)) {
            return appTypeCd + "/category.jsp";
        }
        if ("updateproducterror".equals (operation)) {
            return appTypeCd + "/updateproduct.jsp";
        }
        if ("wishlist".equals(operation)) {
            return appTypeCd + "/wishlist.jsp";
        }
        if ("wishlistsub".equals(operation)) {
            return appTypeCd + "/listwishlist.jsp";
        }
        return "";
    }
}

package com.myappsecurity.sga.vo;

import java.io.Serializable;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Chander Singh
 * @created.on Jan 17, 2008
 */
public class ProductVO implements Serializable, Comparable {
    private String appTypeCd = "";
    private String appName = "";
    private long categoryId = 0;
    private String categoryName = "";
    private long productId = 0;
    private String productCd = "";
    private String productName = "";
    private FileItem productImage = null;
    private String productImg = "";
    private String productPrice = "";
    private String description = "";
    private String sortOrder = "A";
    private String uploaddir = "";
    
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getAppTypeCd() {
        return appTypeCd;
    }

    public void setAppTypeCd(String appTypeCd) {
        this.appTypeCd = appTypeCd;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
    
    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductCd() {
        return productCd;
    }

    public void setProductCd(String productCd) {
        this.productCd = productCd;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.appTypeCd != null ? this.appTypeCd.hashCode() : 0);
        hash = 17 * hash + (this.appName != null ? this.appName.hashCode() : 0);
        hash = 17 * hash + (int) (this.categoryId ^ (this.categoryId >>> 32));
        hash = 17 * hash + (int) (this.productId ^ (this.productId >>> 32));
        return hash;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductVO other = (ProductVO) obj;
        if (this.productId != other.productId) {
            return false;
        }
        return true;
    }
    
    public int compareTo(Object obj) {
        ProductVO productVO = (ProductVO)obj;
        String productCode = productVO.getProductCd();
        if ("A".equals (sortOrder)) {
            return (this.productCd.compareTo(productCode));
        } else {
            return (productCode.compareTo(this.productCd));
        }
    }

    public FileItem getProductImage() {
        return productImage;
    }

    public void setProductImage(FileItem productImage) {
        this.productImage = productImage;                
    }

    public String getUploaddir() {
        return uploaddir;
    }

    public void setUploaddir(String uploaddir) {
        this.uploaddir = uploaddir;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.myappsecurity.sga.vo;

import java.io.Serializable;

/**
 *
 * @author Chander Singh
 * @created.on Jan 17, 2008
 */
public class CategoryVO implements Serializable, Comparable {
    private long categoryId = 0;
    private String appTypeCd = "";
    private String appName = "";
    private String categoryCd = "";
    private String categoryName = "";
    private String description = "";
    private String sortOrder = "A";

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
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

    public String getCategoryCd() {
        return categoryCd;
    }

    public void setCategoryCd(String categoryCd) {
        this.categoryCd = categoryCd;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
    
    public int compareTo(Object obj) {
        CategoryVO categoryVO = (CategoryVO)obj;
        String categoryCode = categoryVO.getCategoryCd();
        if ("A".equals (sortOrder)) {
            return (this.categoryCd.compareTo(categoryCode));
        } else {
            return (categoryCode.compareTo(this.categoryCd));
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.myappsecurity.sga.vo;

import java.io.Serializable;

/**
 *
 * @author csingh
 */
public class AdminVO implements Serializable {
    private String applicationType = "";
    private String applicationName = "";
    private String description = "";
    private String filterStatus = "false";

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilterStatus() {
        return filterStatus;
    }

    public void setFilterStatus(String filterStatus) {
        this.filterStatus = filterStatus;
    }
    
    public boolean equals (Object obj) {
        AdminVO adminVO = (AdminVO) obj;
        String thisappdet = new String (applicationType + "-" + applicationName).toUpperCase();
        String appdet = new String (adminVO.getApplicationType() + "-" + adminVO.getApplicationName()).toUpperCase ();
        return (thisappdet.equals(appdet));        
    }
}

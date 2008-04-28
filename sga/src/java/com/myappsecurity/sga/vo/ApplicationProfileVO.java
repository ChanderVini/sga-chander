/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.myappsecurity.sga.vo;

import java.io.Serializable;

/**
 *
 * @author Chander Singh
 * @created.on Jan 12, 2008
 */
public class ApplicationProfileVO implements Serializable {
    private String appType = "";
    private String appName = "";
    private ModuleVO[] moduleVOs = new ModuleVO[0]; 
    
    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public ModuleVO[] getModuleVOs() {
        return moduleVOs;
    }

    public void setModuleVOs(ModuleVO[] moduleVOs) {
        this.moduleVOs = moduleVOs;
    }
}

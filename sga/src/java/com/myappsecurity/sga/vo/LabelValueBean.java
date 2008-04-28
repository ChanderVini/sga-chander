package com.myappsecurity.sga.vo;

import java.io.Serializable;

/**
 *
 * @author Chander Singh
 * @created.on January 8, 2008
 */
public class LabelValueBean implements Serializable {
    private String label = "";
    private String value = "";
    
    /**
     * 
     * @param label
     * @param value
     */
    public LabelValueBean(String label, String value) {
        this.label = label;
        this.value = value;
    }

    /**
     * 
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     * @return
     */
    public String getValue() {
        return value;
    }    
}

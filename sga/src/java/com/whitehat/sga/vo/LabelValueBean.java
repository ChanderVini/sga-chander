package com.whitehat.sga.vo;

/**
 *
 * @author Chander Singh
 * @created.on January 8, 2008
 */
public class LabelValueBean {
    private String label = "";
    private String value = "";
    
    /**
     * Default constructor of LabelValueBean
     */
    public LabelValueBean(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }    
}

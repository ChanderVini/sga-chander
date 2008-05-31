package com.myappsecurity.sga.vo;

import java.io.Serializable;

/**
 *
 * @author Chander Singh
 * @created.on Mar 27, 2008
 */
public class ContactusVO implements Serializable {
    private String username = "";
    private String email = "";
    private String subject = "";
    private String emailbody = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmailbody() {
        return emailbody;
    }

    public void setEmailbody(String emailbody) {
        this.emailbody = emailbody;
    }    
}

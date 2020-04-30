package com.entity;

import java.io.Serializable;

public class Owner implements Serializable {

    private String oName;

    private String oPassword;

    private String oIdcard;

    private String oMobile;

    private static final long serialVersionUID = 1L;

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName == null ? null : oName.trim();
    }

    public String getoPassword() {
        return oPassword;
    }

    public void setoPassword(String oPassword) {
        this.oPassword = oPassword == null ? null : oPassword.trim();
    }

    public String getoIdcard() {
        return oIdcard;
    }

    public void setoIdcard(String oIdcard) {
        this.oIdcard = oIdcard == null ? null : oIdcard.trim();
    }

    public String getoMobile() {
        return oMobile;
    }

    public void setoMobile(String oMobile) {
        this.oMobile = oMobile == null ? null : oMobile.trim();
    }

    @Override
    public String toString() {
        return "{\"oName\":\"" + oName + "\", \"oPassword\":\"" + oPassword + "\", \"oIdcard\":\"" + oIdcard
                + "\", \"oMobile\":\"" + oMobile + "\"}";
    }

}
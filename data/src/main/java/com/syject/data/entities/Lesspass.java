package com.syject.data.entities;

public class Lesspass {
    private String site;
    private String login;
    private String masterPassword;

    public Lesspass(String site, String login, String masterPassword) {
        this.site = site;
        this.login = login;
        this.masterPassword = masterPassword;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

}

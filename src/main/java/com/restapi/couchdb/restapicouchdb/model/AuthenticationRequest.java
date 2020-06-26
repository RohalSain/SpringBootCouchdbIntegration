package com.restapi.couchdb.restapicouchdb.model;

public class AuthenticationRequest {
    private String username;
    private String password; 

    AuthenticationRequest() {
        
    }
    AuthenticationRequest(String usrString,String pString) {
        this.username = usrString;
        this.password = pString;
    }
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
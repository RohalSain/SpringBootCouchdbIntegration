package com.restapi.couchdb.restapicouchdb.model;

public class AuthenticationRepsonse {
    
    private final String jwt;

    public AuthenticationRepsonse(String jString) {
        this.jwt  = jString;
    }

    public String getJwt() {
        return jwt;
    }
}
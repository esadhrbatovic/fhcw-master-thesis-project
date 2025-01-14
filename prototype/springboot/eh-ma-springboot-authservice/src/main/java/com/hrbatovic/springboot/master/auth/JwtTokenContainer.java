package com.hrbatovic.springboot.master.auth;

import java.util.HashMap;

public class JwtTokenContainer {

    private String jwtToken;
    private HashMap<String, Object> claims;

    public JwtTokenContainer(String jwtToken, HashMap<String, Object> claims) {
        this.jwtToken = jwtToken;
        this.claims = claims;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public JwtTokenContainer setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
        return this;
    }

    public HashMap<String, Object> getClaims() {
        return claims;
    }

    public JwtTokenContainer setClaims(HashMap<String, Object> claims) {
        this.claims = claims;
        return this;
    }
}

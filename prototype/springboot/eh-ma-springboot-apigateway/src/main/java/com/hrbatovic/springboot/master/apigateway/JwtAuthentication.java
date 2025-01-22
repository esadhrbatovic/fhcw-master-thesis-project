package com.hrbatovic.springboot.master.apigateway;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public class JwtAuthentication implements Authentication {


    private final String username;
    private final Collection<? extends GrantedAuthority> authorities;
    private UUID sessionId;
    private UUID userId;
    private String email;
    private String firstName;
    private String lastName;
    private String jwtToken;

    private boolean authenticated = true;

    public JwtAuthentication(String username, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return username;
    }

    public String getUsername() {
        return username;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public JwtAuthentication setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public JwtAuthentication setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public JwtAuthentication setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public JwtAuthentication setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public JwtAuthentication setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public JwtAuthentication setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
        return this;
    }
}

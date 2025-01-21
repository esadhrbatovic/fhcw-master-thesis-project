package com.hrbatovic.springboot.master.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class ClaimUtils {

    public List<String> getRoles(){
        try{
            List <String> roles = getSecurityContext().getAuthorities().stream().map(r->r.toString()).toList();
            return roles;
        }catch(Exception e){
            throw new RuntimeException("Roles can't be determined");
        }
    }

    public UUID getUUIDClaim(String claim){
        if("sid".equalsIgnoreCase(claim)){
            return Objects.requireNonNull(getSecurityContext()).getSessionId();
        }

        if("sub".equalsIgnoreCase(claim)){
            return Objects.requireNonNull(getSecurityContext()).getUserId();
        }

        throw new RuntimeException("Claim " + claim + " not found");
    }

    public String getStringClaim(String claim){

        if("upn".equalsIgnoreCase(claim)){
            return Objects.requireNonNull(getSecurityContext()).getEmail();
        }

        if("email".equalsIgnoreCase(claim)){
            return Objects.requireNonNull(getSecurityContext()).getEmail();
        }

        if("given_name".equalsIgnoreCase(claim)){
            return Objects.requireNonNull(getSecurityContext()).getFirstName();
        }

        if("family_name".equalsIgnoreCase(claim)){
            return Objects.requireNonNull(getSecurityContext()).getLastName();
        }

        throw new RuntimeException("Claim " + claim + " not found");
    }

    private JwtAuthentication getSecurityContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current SecurityContext: " + SecurityContextHolder.getContext().getAuthentication());


        if (authentication instanceof JwtAuthentication) {
            return (JwtAuthentication) authentication;

        }

        throw new RuntimeException("No security context exists");
    }



}

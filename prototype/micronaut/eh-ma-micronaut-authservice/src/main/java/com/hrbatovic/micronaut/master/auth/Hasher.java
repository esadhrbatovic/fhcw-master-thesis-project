package com.hrbatovic.micronaut.master.auth;

import jakarta.inject.Singleton;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Singleton
public class Hasher {

    private PasswordEncoder passwordEncoder;


    public String hash(String text) {
        passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.encode(text);
    }

    public boolean check(String plainText, String hashedText) {
        passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(plainText, hashedText);
    }

}

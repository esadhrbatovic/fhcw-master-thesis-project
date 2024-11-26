package com.hrbatovic.quarkus.master.auth;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Hasher {

    public String hash(String text) {
        return BcryptUtil.bcryptHash(text, 10);
    }

    public boolean check(String plainText, String hashedText) {
        return BcryptUtil.matches(plainText, hashedText);
    }

}
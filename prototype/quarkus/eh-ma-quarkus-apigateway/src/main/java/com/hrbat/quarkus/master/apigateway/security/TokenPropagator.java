package com.hrbat.quarkus.master.apigateway.security;

import io.quarkus.security.credential.TokenCredential;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class TokenPropagator implements ClientRequestFilter {
    @Inject
    SecurityIdentity securityIdentity;

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        TokenCredential tokenCredential = securityIdentity.getCredential(TokenCredential.class);
        if(tokenCredential == null){
            return;
        }
        String token = tokenCredential.getToken();
        if (token == null) {
            return;
        }
        clientRequestContext.getHeaders().add("Authorization", "Bearer " + token);
    }
}
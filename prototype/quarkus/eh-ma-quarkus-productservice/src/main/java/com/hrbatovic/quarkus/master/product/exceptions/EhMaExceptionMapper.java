package com.hrbatovic.quarkus.master.product.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EhMaExceptionMapper implements ExceptionMapper<EhMaException> {

    @Override
    public Response toResponse(EhMaException e) {
        return Response
                .status(e.getErrorCode())
                .entity(e.getMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

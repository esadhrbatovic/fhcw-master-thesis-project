package com.hrbatovic.quarkus.master.order;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/health")
public class HealthApiService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws Exception {

        return "Quarkus authservice is up and running!";
    }

}

package com.hrbatovic.quarkus.master.notification;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/health")
public class HealthResource {


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String health() throws Exception {

        return "Quarkus notificationservice is up and running!";
    }



}

package com.example.tema08.rxnetty;

import netflix.karyon.health.HealthCheckHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(value = "/health")
public class HealthCheckResource implements HealthCheckHandler {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response health() {
        return Response.status(getStatus()).build();
    }

    @Override
    public int getStatus() {
        return 200;
    }
}

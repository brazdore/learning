package com.github.diegopacheco.sandbox.java.netflixoss.karyon.rest;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("/math")
public class ExpResource {

    private static final Logger logger = LoggerFactory.getLogger(ExpResource.class);

    private ExpService service;

    @Inject
    public ExpResource(ExpService service) {
        this.service = service;
    }

    @GET
    @Path("exp/{a}/{b}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response set(@PathParam("a") Double a, @PathParam("b") Double b) {
        try {
            return Response.ok(service.exp(a, b) + "").build();
        } catch (Exception e) {
            logger.error("Error creating json response.", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}

package com.github.diegopacheco.sandbox.java.netflixoss.karyon.rest;

import com.github.diegopacheco.sandbox.java.netflixoss.karyon.hystrix.SimpleCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("/math")
public class CalcResource {

    private static final Logger logger = LoggerFactory.getLogger(CalcResource.class);

    private final CalcService service;

    @Inject
    public CalcResource(CalcService service) {
        this.service = service;
    }

    @GET
    @Path("calc/{op}/{x}/{y}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response set(@PathParam("op") String op, @PathParam("x") String x, @PathParam("y") String y) {
        try {
            Double doubleX = Double.valueOf(x);
            Double doubleY = Double.valueOf(y);
            return Response.ok(String.valueOf(service.calc(op, doubleX, doubleY))).build();
        } catch (Exception e) {
            logger.error("Error creating json response.", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("ops")
    @Produces(MediaType.TEXT_PLAIN)
    public Response ops() {
        try {

            String result = new SimpleCommand("sum; sub; div; mul; exp").observe().toBlocking().first();
            return Response.ok(result).build();

        } catch (Exception e) {
            logger.error("Error creating json response.", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

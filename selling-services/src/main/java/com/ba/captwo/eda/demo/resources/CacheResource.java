package com.ba.captwo.eda.demo.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by justin on 21/09/2016.
 */
@Path("/cache")
public interface CacheResource {

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/add")
    public Response addEntry(
            @MatrixParam("val") String val);


    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/list")
    public Response listEntries();


    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/clear")
    public Response clearEntries();
}

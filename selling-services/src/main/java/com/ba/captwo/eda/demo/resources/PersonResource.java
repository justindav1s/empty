package com.ba.captwo.eda.demo.resources;

import com.ba.captwo.eda.demo.model.Person;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by u760245 on 05/07/2014.
 */

@CrossOriginResourceSharing(
        allowAllOrigins = true,
        allowCredentials = true
)

@Path("/person")
public interface PersonResource {


    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/qcreate")
    public Response createPersonQueryString(
            @MatrixParam("fname") String fname,
            @MatrixParam("lname") String lname,
            @MatrixParam("address") String address,
            @MatrixParam("city") String city);


    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/create")
    public Response createPerson(Person p);

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/read")
    public Response readPerson(
            @MatrixParam("pid") int pid);

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/update")
    public Response updatePerson(@MatrixParam("pid") int pid, Person p);

    @DELETE
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/delete")
    public Response deletePerson(
            @MatrixParam("pid") int pid);

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/list")
    public Response listPersons();
}

package com.ba.captwo.eda.demo.resources;

import com.ba.captwo.eda.demo.model.Person;
import com.google.common.collect.ImmutableMap;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by u760245 on 15/07/2014.
 */
@Path("/personasync")
public class PersonAsyncRouteResource implements PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonAsyncRouteResource.class);

    CamelContext camelContext = new DefaultCamelContext();

    @Override
    public Response createPerson(Person p) {

        Response response = null;
        Object objCamelOut = null;

        try {

            ProducerTemplate t = camelContext.createProducerTemplate();
            Future<Object> future = t.asyncRequestBody("direct-vm:selling.services.person.create", p);

            //do loads of other stuff

            objCamelOut = t.extractFutureBody(future, Object.class);

            response = Response.status(Response.Status.OK).entity(objCamelOut).build();
        }
        catch (Exception e) {
            com.ba.captwo.eda.demo.model.Error err = new com.ba.captwo.eda.demo.model.Error();
            err.setMessage(e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }

        log.debug("Camel Out Object : "+objCamelOut);

        return response;
    }

    public Response createPersonQueryString(
            String fname,
            String lname,
            String address,
            String city) {return null;}


    @Override
    public Response readPerson(int pid) {

        Response response = null;
        Object objCamelOut = null;

        try {
            Map<String, Object> personDetails = ImmutableMap.of("pid", (Object) pid);

            ProducerTemplate t = camelContext.createProducerTemplate();
            Future<Object> future = t.asyncRequestBodyAndHeaders("direct-vm:selling.services.person.read", null, personDetails);

            //do loads of other stuff

            objCamelOut = t.extractFutureBody(future, Object.class);

            response = Response.status(Response.Status.OK).entity(objCamelOut).build();
        }
        catch (Exception e) {
            com.ba.captwo.eda.demo.model.Error err = new com.ba.captwo.eda.demo.model.Error();
            err.setMessage(e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }

        log.debug("Camel Out Object : "+objCamelOut);

        return response;
    }

    @Override
    public Response updatePerson(int pid, Person p) {
        return null;
    }

    @Override
    public Response deletePerson(int pid) {

        Response response = null;
        Object objCamelOut = null;

        try {
            Map<String, Object> personDetails = ImmutableMap.of("pid", (Object) pid);

            ProducerTemplate t = camelContext.createProducerTemplate();
            Future<Object> future = t.asyncRequestBodyAndHeaders("direct-vm:selling.services.person.read", null, personDetails);

            //do loads of other stuff

            objCamelOut = t.extractFutureBody(future, Object.class);

            response = Response.status(Response.Status.OK).entity(objCamelOut).build();
        }
        catch (Exception e) {
            com.ba.captwo.eda.demo.model.Error err = new com.ba.captwo.eda.demo.model.Error();
            err.setMessage(e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }

        log.debug("Camel Out Object : "+objCamelOut);

        return response;
    }

    @Override
    public Response listPersons() {
        return null;
    }
}

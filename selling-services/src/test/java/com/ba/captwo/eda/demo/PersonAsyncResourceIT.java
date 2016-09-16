package com.ba.captwo.eda.demo;

import com.ba.captwo.eda.demo.model.Person;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PersonAsyncResourceIT {
    private static String endpointUrl;
    private final static Logger log = LoggerFactory.getLogger(PersonAsyncResourceIT.class);

    @BeforeClass
    public static void beforeClass() {
        log.info("***** System.getProperty : " + System.getProperty("service.url"));
        endpointUrl = System.getProperty("service.url");

        if(endpointUrl == null) {
            endpointUrl = "http://localhost:8080/selling";
        }

        log.info("***** endpointUrl : " + endpointUrl);
    }



    @Test
    public void testRead() throws Exception {


        log.info("Test READ");
        log.info("endpointUrl : "+endpointUrl);

        Person p = createPerson();

        WebClient client = WebClient.create(endpointUrl + "/personasync/read;pid="+p.getPersonID());
        Response r = client.accept("application/json").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("Read response : "+value);

        ObjectMapper mapper = new ObjectMapper();
        Person p2 = mapper.readValue(value, Person.class);
        log.info("Read Person : "+p2.toString());

        assertNotNull(p2);
        assertNotNull(p2.getFirstName());
        assertNotNull(p2.getLastName());
        assertNotNull(p2.getAddress());
        assertNotNull(p2.getCity());

        deletePerson(p2);
    }


    private Person createPerson()  throws Exception {

        List<Object> providers = new ArrayList<Object>();
        providers.add( new JacksonJsonProvider());

        Person p = buildPerson();

        String uri = "/personasync/create";

        WebClient client = WebClient.create(endpointUrl + uri, providers);
        Response r = client.accept("application/json").type("application/json").post(p);

        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream) r.getEntity());
        log.info("Create response : " + value);

        ObjectMapper mapper = new ObjectMapper();
        p = mapper.readValue(value, Person.class);
        log.info("Create pd : " + p.getPersonID());
        return p;

    }

    private void deletePerson(Person p)  throws Exception {

        String uri = "/personasync/delete;;pid="+p.getPersonID();

        WebClient client = WebClient.create(endpointUrl + uri);
        Response r = client.accept("application/json").delete();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream) r.getEntity());
        log.info("delete response : " + value);

    }

    private Person buildPerson()    {
        Person testPerson = new Person();
        testPerson.setLastName("Fry");
        testPerson.setFirstName("Stephen");
        testPerson.setAddress("Norwich");
        testPerson.setCity("Norfolk");
        return testPerson;
    }
}

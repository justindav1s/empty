package com.ba.captwo.eda.demo;

import com.ba.captwo.eda.demo.db.PersonDAO;
import com.ba.captwo.eda.demo.model.Person;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test_beans.xml"})
public class PersonResourceIT {
    private static String endpointUrl;
    private final static Logger log = LoggerFactory.getLogger(PersonResourceIT.class);
    @Autowired
    PersonDAO personDAO;

    @BeforeClass
    public static void beforeClass() {
        endpointUrl = System.getProperty("service.url");
    }


    @Test
    public void testCreatePersonWithQueryString()  throws Exception {

        List<Object> providers = new ArrayList<Object>();
        providers.add( new JacksonJsonProvider());

        log.info("***** createPerson START");
        Person p = new Person();
        p.setLastName("Windsor");
        p.setFirstName("William");
        p.setAddress("Kensington Palace");
        p.setCity("London");

        String uri = "/person/qcreate;fname="+p.getFirstName()+";lname="+p.getLastName()+";address="+p.getAddress()+";city="+p.getCity();

        log.info("***** URI :" + endpointUrl + uri);

        WebClient client = WebClient.create(endpointUrl + uri, providers);
        Response r = client.accept("application/json").type("application/json").post("");
        String value = IOUtils.toString((InputStream) r.getEntity());
        log.info("Create response : " + value);

        ObjectMapper mapper = new ObjectMapper();
        Person p2 = mapper.readValue(value, Person.class);
        log.info("Create pd : " + p2.getPersonID());

        log.info("***** createPerson END");


    }

    @Test
    public void testCreate() throws Exception {
        log.info("Test CREATE POST");

        List<Object> providers = new ArrayList<Object>();
        providers.add( new JacksonJsonProvider());

        Person testPerson = new Person();
        testPerson.setLastName("Windsor");
        testPerson.setFirstName("William");
        testPerson.setAddress("Kensington Palace");
        testPerson.setCity("London");

        String uri = "/person/create";

        WebClient client = WebClient.create(endpointUrl + uri, providers);
        Response r = client.accept("application/json").type("application/json").post(testPerson);
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("Create response : "+value);

        ObjectMapper mapper = new ObjectMapper();
        Person p = mapper.readValue(value, Person.class);
        log.info("Create pd : "+p.getPersonID());

        personDAO.deletePerson(p.getPersonID());
    }


    @Test
    public void testRead() throws Exception {
        log.info("Test READ");

        Person testPerson = new Person();
        testPerson.setLastName("Windsor");
        testPerson.setFirstName("William");
        testPerson.setAddress("Kensington Palace");
        testPerson.setCity("London");

        testPerson = personDAO.createPerson(testPerson);

        WebClient client = WebClient.create(endpointUrl + "/person/read;pid="+testPerson.getPersonID());
        Response r = client.accept("application/json").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("Read response : "+value);

        personDAO.deletePerson(testPerson.getPersonID());
    }

    @Test
    public void testReadRepeat() throws Exception {
        log.info("Test READ REPEAT START");

        Person testPerson = new Person();
        testPerson.setLastName("Windsor");
        testPerson.setFirstName("William");
        testPerson.setAddress("Kensington Palace");
        testPerson.setCity("London");

        testPerson = personDAO.createPerson(testPerson);

        WebClient client = WebClient.create(endpointUrl + "/person/read;pid="+testPerson.getPersonID());
        Response r = client.accept("application/json").get();
        showHeaders(r);
        List<Object> cookie = getSessionCookie(r);
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("Read response : "+value);

        client = WebClient.create(endpointUrl + "/person/read;pid="+testPerson.getPersonID());
        r = client.accept("application/json").header("Cookie", cookie).get();
        showHeaders(r);
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        value = IOUtils.toString((InputStream)r.getEntity());
        log.info("Read response : "+value);

        personDAO.deletePerson(testPerson.getPersonID());

        log.info("Test READ REPEAT END");
    }

    @Test
    public void testUpdate() throws Exception {
        log.info("Test UPDATE START");

        List<Object> providers = new ArrayList<Object>();
        providers.add( new JacksonJsonProvider());

        String oldLame = "Windsor";
        String newLname = "Cambridge";

        Person testPerson = new Person();
        testPerson.setLastName(oldLame);
        testPerson.setFirstName("William");
        testPerson.setAddress("Kensington Palace");
        testPerson.setCity("London");

        testPerson = personDAO.createPerson(testPerson);

        String uri = "/person/update;pid="+testPerson.getPersonID();

        //change name
        testPerson.setLastName(newLname);

        WebClient client = WebClient.create(endpointUrl + uri, providers);
        Response r = client.accept("application/json").type("application/json").put(testPerson);
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("Update response : "+value);

        ObjectMapper mapper = new ObjectMapper();
        Person p = mapper.readValue(value, Person.class);
        log.info("Update pid : "+p.getPersonID());
        log.info("Updated lname : "+p.getLastName());
        Assert.assertEquals(newLname, p.getLastName());

        Person p2 = personDAO.readPerson(p.getPersonID());
        Assert.assertEquals(newLname, p2.getLastName());

        personDAO.deletePerson(p.getPersonID());

    }


    @Test
    public void testDelete() throws Exception {
        log.info("Test DELETE");

        Person testPerson = new Person();
        testPerson.setLastName("Windsor");
        testPerson.setFirstName("William");
        testPerson.setAddress("Kensington Palace");
        testPerson.setCity("London");

        testPerson = personDAO.createPerson(testPerson);

        WebClient client = WebClient.create(endpointUrl + "/person/delete;pid="+testPerson.getPersonID());
        Response r = client.accept("application/json").delete();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("Delete response : "+value);
        assertEquals("OK", value);

        personDAO.deletePerson(testPerson.getPersonID());
    }

    @Test
    public void testList() throws Exception {
        log.info("Test LIST");

        Person testPerson = new Person();
        testPerson.setLastName("Windsor");
        testPerson.setFirstName("William");
        testPerson.setAddress("Kensington Palace");
        testPerson.setCity("London");

        testPerson = personDAO.createPerson(testPerson);

        WebClient client = WebClient.create(endpointUrl + "/person/list");
        Response r = client.accept("application/json").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        log.info("List response : "+value);

        ObjectMapper mapper = new ObjectMapper();
        Person[] persons = mapper.readValue(value, Person[].class);
        assertTrue(persons.length > 0);

        personDAO.deletePerson(testPerson.getPersonID());
    }

    public void showHeaders(Response r) {
        log.debug("HEADERs : ");
        MultivaluedMap<String, Object> headers = r.getHeaders();
        for(MultivaluedMap.Entry<String, List<Object>> header : headers.entrySet())  {
            log.debug("HEADER : " + header.getKey() + " : "+ header.getValue());
        }
    }

    public List<Object> getSessionCookie(Response r) {
        log.debug("HEADERs : ");
        MultivaluedMap<String, Object> headers = r.getHeaders();
        return headers.get("Set-Cookie");
    }
}

package com.ba.captwo.eda.demo.routes;

import com.ba.captwo.eda.demo.db.FlightDAO;
import com.ba.captwo.eda.demo.db.PersonDAO;
import com.ba.captwo.eda.demo.model.Flight;
import com.ba.captwo.eda.demo.model.Person;
import com.google.common.collect.ImmutableMap;
import org.apache.camel.*;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by u760245 on 14/07/2014.
 */
@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/camel_beans.xml" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpoints("log:*")
@DisableJmx(false)
public class CamelFuturesTest {

    private final static Logger log = LoggerFactory.getLogger(CamelFuturesTest.class);

    @Autowired
    private FlightDAO flightDAO;

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    protected CamelContext camelContext;


    @Test
    public void testInvestigate() throws Exception {

        List<String> compNames = camelContext.getComponentNames();
        for (String compName : compNames)   {
            log.debug("COMP : "+compName);
        }
        Map <String, Endpoint> endpoints = camelContext.getEndpointMap();
        for(Map.Entry<String, Endpoint> ep : endpoints.entrySet())  {
            log.debug("EP : "+ep.getKey()+" : "+ep.getValue());
        }
        List<Route> routes = camelContext.getRoutes();
        for(Route r : routes)   {
            log.debug("Route : "+r.getId());
        }
    }

    @Test
    public void testSyncReadPerson() throws Exception {


        Person p = buildPerson();
        Person pcreated = personDAO.createPerson(p);

        Map<String, Object> personDetails = ImmutableMap.of("pid", (Object)pcreated.getPersonID());

        ProducerTemplate t = camelContext.createProducerTemplate();
        Person pCamelOut = (Person)t.requestBodyAndHeaders("direct-vm:selling.services.person.read", null, personDetails);

        assertEquals(pCamelOut.getPersonID(), pcreated.getPersonID());

        log.debug("Created Person : pid = "+pcreated.getPersonID());
        log.debug("Camel Out Person : pid = "+pCamelOut.getPersonID());
        personDAO.deletePerson(pcreated.getPersonID());
    }


    @Test
    public void testASyncReadPerson() throws Exception {

        Person p = buildPerson();
        Person pcreated = personDAO.createPerson(p);
        Map<String, Object> personDetails = ImmutableMap.of("pid", (Object)pcreated.getPersonID());

        ProducerTemplate t = camelContext.createProducerTemplate();
        Future<Object> future = t.asyncRequestBodyAndHeaders("direct-vm:selling.services.person.read", null, personDetails);

        //snooze

        Person pCamelOut = t.extractFutureBody(future, Person.class);

        assertEquals(pCamelOut.getPersonID(), pcreated.getPersonID());

        log.debug("Created Person : pid = "+pcreated.getPersonID());
        log.debug("Camel Out Person : pid = "+pCamelOut.getPersonID());

        personDAO.deletePerson(pcreated.getPersonID());
    }

    @Test
    public void testSyncReadFlight() throws Exception {

        Flight f = buildFlight();
        Flight fcreated = flightDAO.createFlight(f);

        Map<String, Object> flightDetails = ImmutableMap.of("fnum", (Object)fcreated.getFlightnum());

        ProducerTemplate t = camelContext.createProducerTemplate();
        Flight fCamelOut = (Flight)t.requestBodyAndHeaders("direct-vm:selling.services.flight.read", null, flightDetails);

        assertEquals(fCamelOut.getDestination(), fcreated.getDestination());
        assertEquals(fCamelOut.getOrigin(), fcreated.getOrigin());

        log.debug("Created Flight : dest = "+fcreated.getDestination());
        log.debug("Camel Out Flight : dest = "+fCamelOut.getDestination());
        flightDAO.deleteFlight(fcreated.getFlightnum());
    }

    @Test
    public void testASyncReadFlight() throws Exception {

        Flight f = buildFlight();
        Flight fcreated = flightDAO.createFlight(f);

        Map<String, Object> flightDetails = ImmutableMap.of("fnum", (Object)fcreated.getFlightnum());

        ProducerTemplate t = camelContext.createProducerTemplate();
        Future<Object> future = t.asyncRequestBodyAndHeaders("direct-vm:selling.services.flight.read", null, flightDetails);

        //snooze

        Flight fCamelOut = t.extractFutureBody(future, Flight.class);

        assertEquals(fCamelOut.getDestination(), fcreated.getDestination());
        assertEquals(fCamelOut.getOrigin(), fcreated.getOrigin());

        log.debug("Created Flight : dest = "+fcreated.getDestination());
        log.debug("Camel Out Flight : dest = "+fCamelOut.getDestination());

        flightDAO.deleteFlight(fcreated.getFlightnum());
    }

    @Test
    public void testSyncReadFlightAndPerson() throws Exception {

        Flight f = buildFlight();
        Flight fcreated = flightDAO.createFlight(f);
        Map<String, Object> flightDetails = ImmutableMap.of("fnum", (Object)fcreated.getFlightnum());

        Person p = buildPerson();
        Person pcreated = personDAO.createPerson(p);
        Map<String, Object> personDetails = ImmutableMap.of("pid", (Object)pcreated.getPersonID());

        ProducerTemplate t = camelContext.createProducerTemplate();

        long start = System.currentTimeMillis();
        Flight fCamelOut = (Flight)t.requestBodyAndHeaders("direct-vm:selling.services.flight.read", null, flightDetails);
        Person pCamelOut = (Person)t.requestBodyAndHeaders("direct-vm:selling.services.person.read", null, personDetails);
        long end = System.currentTimeMillis();

        assertEquals(fCamelOut.getDestination(), fcreated.getDestination());
        assertEquals(fCamelOut.getOrigin(), fcreated.getOrigin());
        assertEquals(pCamelOut.getPersonID(), pcreated.getPersonID());

        log.debug("Created Flight : dest = "+fcreated.getDestination());
        log.debug("Camel Out Flight : dest = "+fCamelOut.getDestination());
        log.debug("Created Person : pid = "+pcreated.getPersonID());
        log.debug("Camel Out Person : pid = "+pCamelOut.getPersonID());
        log.debug("Sync Duration :  "+(end-start)+" ms");

        flightDAO.deleteFlight(fcreated.getFlightnum());
        personDAO.deletePerson(pcreated.getPersonID());
    }

    @Test
    public void testZSyncReadFlightAndPerson() throws Exception {

        Flight f = buildFlight();
        Flight fcreated = flightDAO.createFlight(f);
        Map<String, Object> flightDetails = ImmutableMap.of("fnum", (Object)fcreated.getFlightnum());

        Person p = buildPerson();
        Person pcreated = personDAO.createPerson(p);
        Map<String, Object> personDetails = ImmutableMap.of("pid", (Object)pcreated.getPersonID());

        ProducerTemplate t = camelContext.createProducerTemplate();

        long start = System.currentTimeMillis();
        Future<Object> flightFuture = t.asyncRequestBodyAndHeaders("direct-vm:selling.services.flight.read", null, flightDetails);
        Future<Object> personFuture = t.asyncRequestBodyAndHeaders("direct-vm:selling.services.person.read", null, personDetails);

        //snooze

        Flight flightCamelOut = t.extractFutureBody(flightFuture, Flight.class);
        Person personCamelOut = t.extractFutureBody(personFuture, Person.class);
        long end = System.currentTimeMillis();

        assertEquals(flightCamelOut.getDestination(), fcreated.getDestination());
        assertEquals(flightCamelOut.getOrigin(), fcreated.getOrigin());
        assertEquals(personCamelOut.getPersonID(), pcreated.getPersonID());

        log.debug("Created Flight : dest = "+fcreated.getDestination());
        log.debug("Camel Out Flight : dest = "+flightCamelOut.getDestination());
        log.debug("Created Person : pid = "+pcreated.getPersonID());
        log.debug("Camel Out Person : pid = "+personCamelOut.getPersonID());
        log.debug("Async Duration :  "+(end-start)+" ms");

        flightDAO.deleteFlight(fcreated.getFlightnum());
        personDAO.deletePerson(pcreated.getPersonID());
    }

    private Person buildPerson()    {
        Person p = new Person();
        p.setFirstName("Nikola");
        p.setLastName("Tesla");
        p.setAddress("Edison Machine Works");
        p.setCity("New York");
        return p;
    }

    private Flight buildFlight()    {
        Flight f = new Flight();
        f.setFlightnum("BA176");
        f.setOrigin("LHR");
        f.setDestination("NYC");
        return f;
    }
}

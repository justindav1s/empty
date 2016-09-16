package com.ba.captwo.eda.demo.routes;

import com.ba.captwo.eda.demo.db.PersonDAO;
import com.ba.captwo.eda.demo.model.Person;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
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

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by u760245 on 12/07/2014.
 */
@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/camel_beans.xml" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpoints("log:*")
@DisableJmx(false)
public class PersonCoreserviceRouteTest {

    private final static Logger log = LoggerFactory.getLogger(PersonCoreserviceRouteTest.class);

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    protected CamelContext camelContext;

    @EndpointInject(uri = "mock:log:output")
    protected MockEndpoint mockEndPoint;

    @Test
    public void testCreatePerson() throws Exception {

        Person p = buildPerson();

        mockEndPoint.setExpectedMessageCount(1);
        mockEndPoint.message(0).body().isNotNull();
        mockEndPoint.message(0).body().isInstanceOf(Person.class);

        ProducerTemplate t = camelContext.createProducerTemplate();
        t.sendBody("direct-vm:selling.services.person.create", p);
        MockEndpoint.assertIsSatisfied(camelContext);

    }

    @Test
    public void testCreatePerson2() throws Exception {

        Person p = buildPerson();

        ProducerTemplate t = camelContext.createProducerTemplate();
        Person pout = (Person)t.requestBody("direct-vm:selling.services.person.create", p);

        assertNotNull(pout);
        assertTrue(pout.getPersonID() > 0);

        log.debug("Created Person : pid = "+pout.getPersonID());
        personDAO.deletePerson(pout.getPersonID());
    }

    @Test
    public void testReadPerson() throws Exception {

        Person p = buildPerson();
        p = personDAO.createPerson(p);

        int pid = p.getPersonID();
        log.debug("Created Person : pid = "+pid);

        mockEndPoint.setExpectedMessageCount(1);
        mockEndPoint.message(0).body().isNotNull();
        mockEndPoint.message(0).header("pid").isEqualTo(pid);
        mockEndPoint.message(0).body().isInstanceOf(Person.class);
        //mockEndPoint.message(0).body().isEqualTo(p);

        ProducerTemplate t = camelContext.createProducerTemplate();
        t.sendBodyAndHeader("direct-vm:selling.services.person.read", pid, "pid", pid);
        MockEndpoint.assertIsSatisfied(camelContext);

        personDAO.deletePerson(pid);
    }

    @Test
    public void testUpdatePerson() throws Exception {

        String newAddress = "Ehrenbergstrasse 33";

        Person p = buildPerson();
        p = personDAO.createPerson(p);

        p.setAddress(newAddress);

        mockEndPoint.setExpectedMessageCount(1);
        mockEndPoint.message(0).body().isNotNull();
        mockEndPoint.message(0).body().isInstanceOf(Person.class);

        ProducerTemplate t = camelContext.createProducerTemplate();
        t.sendBody("direct-vm:selling.services.person.update", p);

        MockEndpoint.assertIsSatisfied(camelContext);

        Person p2 = personDAO.readPerson(p.getPersonID());
        assertEquals(p.getAddress(), p2.getAddress());

        personDAO.deletePerson(p.getPersonID());
    }

    @Test
    public void testDeletePerson() throws Exception {

        Person p = buildPerson();
        p = personDAO.createPerson(p);

        mockEndPoint.setExpectedMessageCount(1);
        mockEndPoint.message(0).body().isNull();

        ProducerTemplate t = camelContext.createProducerTemplate();
        t.sendBodyAndHeader("direct-vm:selling.services.person.delete", null, "pid", p.getPersonID());

        MockEndpoint.assertIsSatisfied(camelContext);

        Person p2 = personDAO.readPerson(p.getPersonID());
        assertNull(p2.getFirstName());
        assertNull(p2.getLastName());

    }


    @Test
    public void testListPersons() throws Exception {

        mockEndPoint.setExpectedMessageCount(1);
        mockEndPoint.message(0).body().isNotNull();
        mockEndPoint.message(0).body().isInstanceOf(ArrayList.class);

        ProducerTemplate t = camelContext.createProducerTemplate();
        t.sendBody("direct-vm:selling.services.person.list", null);

        MockEndpoint.assertIsSatisfied(camelContext);

    }

    private Person buildPerson()    {
        Person p = new Person();
        p.setFirstName("Albert");
        p.setLastName("Einstein");
        p.setAddress("Wittelbacher Strasse 13");
        p.setCity("Berlin");
        return p;
    }
}

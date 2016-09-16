package com.ba.captwo.eda.demo;

import com.ba.captwo.eda.demo.db.PersonDAO;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test_beans.xml"})
public class LocalIT {

    private final static Logger log = LoggerFactory.getLogger(LocalIT.class);
    @Autowired
    PersonDAO personDAO;

    private final static String ENDPOINT_ADDRESS = "ba://services";
    JAXRSClientFactoryBean cf = null;
    private WebClient client;

    @Before
    public void setUp() throws Exception {
        log.info("* setUp 1");
        cf = new JAXRSClientFactoryBean();
        log.info("* setUp 2");
        cf.setAddress(ENDPOINT_ADDRESS);
        log.info("* setUp 3");

    }

    @Test
    public void testLocalRead() throws Exception {
    /**
        Response response = null;
        String responseString = null;
        client = cf.createWebClient();
        WebClient.getConfig(client).getRequestContext()
                .put(BAConduit.DIRECT_DISPATCH, Boolean.TRUE);

        log.info("* Test Local READ 1 1");
        response = client
                .path("hello/echo/one")
                .type(MediaType.WILDCARD)
                .accept(MediaType.WILDCARD)
                .get(Response.class);
        log.info("* Test Local READ 1 2");
        responseString = response.getEntity().toString();
        log.info("* RESPONSE : "+ responseString);
        assertEquals(200, response.getStatus());
        log.info("* Test Local READ 1 3");

        client = cf.createWebClient();
        WebClient.getConfig(client).getRequestContext()
                .put(BAConduit.DIRECT_DISPATCH, Boolean.TRUE);

        log.info("* Test Local READ 2 1");
        response = client
                .path("hello/echo/two")
                .type(MediaType.WILDCARD)
                .accept(MediaType.WILDCARD)
                .get(Response.class);
        log.info("* Test Local READ 2 2");
        responseString = response.getEntity().toString();
        log.info("* RESPONSE : "+ responseString);
        assertEquals(200, response.getStatus());
        log.info("* Test Local READ 2 3");

        */


    }

}

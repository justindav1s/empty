package com.ba.captwo.eda.demo.pact;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import com.ba.captwo.eda.demo.clients.PersonClient;
import com.ba.captwo.eda.demo.model.Error;
import com.ba.captwo.eda.demo.model.Person;
import com.ba.captwo.eda.demo.model.Reservation;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PersonCreateNoFirstNameConsumerPactTest {

    private static String mockserverHost = "localhost";
    private static int mockserverPort = 9080;
    private static String uri = "/person/create";
    private static String url = null;
    private static String inputbody = null;
    private static Map<String, String> inputHeaders = null;
    private static PactDslJsonBody outputbody = null;
    private static Map<String, String> outputHeaders = null;

    @BeforeClass
    public static void before() {
        url = "http://"+mockserverHost+":"+mockserverPort+uri;
        inputbody = buildRequestBody().toJson();
        inputHeaders = buildInputHeaders(inputbody.length());
        outputbody = buildResponseBody();
        outputHeaders = buildOutputHeaders();
    }

    public static Person buildRequestBody()   {
        Person p = new Person();
        p.setLastName("Obama");
        p.setAddress("The White House");
        p.setCity("Washington");
        return p;
    }

    private static PactDslJsonBody buildResponseBody()   {
        PactDslJsonBody body = new PactDslJsonBody()
                .stringValue("message", "invalid person message - no first name");
        return body;
    }

    private static Map<String, String> buildInputHeaders(int contentLength)  {
        Map<String, String> inputheaders = new HashMap<String, String>();
        inputheaders.put("Accept", "application/json, application/*+json");
        inputheaders.put("Connection", "keep-alive");
        //inputheaders.put("Content-Length", String.valueOf(contentLength));
        inputheaders.put("Content-Type", "application/json; charset=UTF-8");
        //inputheaders.put("User-Agent", "Java/1.8.0_60");
        //inputheaders.put("Host", mockserverHost+":"+mockserverPort);
        inputheaders.put("client_name", "uber_app");
        return inputheaders;
    }

    private static Map<String, String> buildOutputHeaders()  {
        Map<String, String> outputheaders = new HashMap<String, String>();
        outputheaders.put("client_name", "uber_app");
        outputheaders.put("Content-Type", "application/json");
        return outputheaders;
    }


    @Rule
    public PactProviderRule provider = new PactProviderRule("person_provider", mockserverHost, mockserverPort, this);

    @Pact(provider="person_provider", consumer="person_consumer")
    public PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("client_name", "uber_app");

        return builder
                .given("we are saving a person's details to the repository")
                .uponReceiving("A create person request with no first name")
                .path(uri)
                .method("POST")
                .headers(inputHeaders)
                .body(inputbody)
                .willRespondWith()
                .status(400)
                .headers(outputHeaders)
                .body(outputbody)
                .toFragment();
    }


    @Test
    @PactVerification("person_provider")
    public void runTest2() throws IOException {

        PersonClient client = new PersonClient();
        ResponseEntity<Person> response = client.createPerson(buildRequestBody(), url, inputHeaders);
        //Bad Person 1
        assertEquals(response.getStatusCodeValue(), 400);

    }
}
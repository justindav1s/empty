package com.ba.captwo.eda.demo.pact;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import com.ba.captwo.eda.demo.model.Error;
import com.ba.captwo.eda.demo.model.Person;
import com.ba.captwo.eda.demo.model.Reservation;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PersonCreateNoFirstNameConsumerPactTest {

    public Person buildPerson()   {
        Person p = new Person();
        p.setLastName("Obama");
        p.setFirstName("Barack");
        p.setAddress("The White House");
        p.setCity("Washington");
        return p;
    }

    public Person buildOKPerson()   {
        Person p = buildPerson();
        p.setPersonID(1000);
        return p;
    }

    public Person buildBadPersonNoFirstName()   {
        Person p = buildPerson();
        p.setFirstName(null);
        return p;
    }

    public Person buildBadPersonNoLastName()   {
        Person p = buildPerson();
        p.setLastName(null);
        return p;
    }

    public Reservation buildReservationErrorResponse(String message)   {
        Reservation r = new Reservation();
        r.setError(new Error().setMessage(message));
        return r;
    }


    @Rule
    public PactProviderRule provider = new PactProviderRule("person_provider", "localhost", 8080, this);

    @Pact(provider="person_provider", consumer="person_consumer")
    public PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("client_name", "uber_app");

        return builder
                .given("person details added to repository")
                .uponReceiving("a save person request with no first name")
                .path("/person/create")
                .method("POST")
                .headers(headers)
                .body(buildBadPersonNoFirstName().toJson())
                .willRespondWith()
                .status(400)
                .headers(headers)
                .body(buildReservationErrorResponse("invalid person message - no first name").toJson())
                .toFragment();
    }


    @Test
    @PactVerification("person_provider")
    public void runTest2() throws IOException {
        BasicHeader header = new BasicHeader("client_name", "uber_app");

        //Bad Person 1
        assertEquals(new ConsumerClient("http://localhost:8080").postForStatusCode("/person/create", buildBadPersonNoFirstName().toJson(), header, ContentType.APPLICATION_JSON), 400);

    }
}
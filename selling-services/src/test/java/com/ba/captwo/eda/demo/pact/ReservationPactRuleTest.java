package com.ba.captwo.eda.demo.pact;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import com.ba.captwo.eda.demo.model.*;
import com.ba.captwo.eda.demo.model.Error;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/camel_beans.xml" })
public class ReservationPactRuleTest {

    public Reservation buildReservationRequest()   {

        Reservation res = new Reservation();

        Person p1 = new Person();
        p1.setLastName("Windsor");
        p1.setFirstName("William");
        p1.setAddress("Kensington Palace");
        p1.setCity("London");
        res.getPersons().add(p1);

        Person p2 = new Person();
        p2.setLastName("Windsor");
        p2.setFirstName("William");
        p2.setAddress("Kensington Palace");
        p2.setCity("London");
        res.getPersons().add(p2);

        Flight f1 = new Flight();
        f1.setFlightnum("BA177");
        f1.setOrigin("LHR");
        f1.setDestination("JFK");
        res.getItinerary().add(f1);

        Flight f2 = new Flight();
        f2.setFlightnum("BA178");
        f2.setOrigin("JFK");
        f2.setDestination("LHR");
        res.getItinerary().add(f2);

        return res;

    }

    public Reservation buildReservationResponse()   {
        Reservation r = buildReservationRequest();
        r.setBookingId("ABCDEF");
        return r;
    }

    public Reservation buildReservationErrorResponse(String message)   {
        Reservation r = new Reservation();
        r.setError(new Error().setMessage(message));
        return r;
    }

    public Reservation buildReservationbadItinerary()   {
        Reservation badItinerary = buildReservationRequest();
        badItinerary.getItinerary().clear();
        return badItinerary;
    }

    public Reservation buildReservationNoPassengers() {
        Reservation r1 = buildReservationRequest();
        r1.getPersons().clear();
        return r1;
    }

    @Rule
    public PactProviderRule provider = new PactProviderRule("reservation_provider", "localhost", 8080, this);

    @Pact(provider="reservation_provider", consumer="reservation_consumer")
    public PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("client_name", "uber_app");

        String greenPathRequest = buildReservationRequest().toJson();
        String greenPathRespoonse = buildReservationRequest().toJson();
        Reservation r1 = buildReservationRequest();
        r1.getPersons().clear();
        String badRequestNoPeople = r1.toJson();




        return builder
                .given("new reservation is being requested")
                .uponReceiving("a valid make reservation request")
                .path("/makereservation")
                .method("POST")
                .headers(headers)
                .body(buildReservationRequest().toJson())
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(buildReservationResponse().toJson())
                .uponReceiving("a reservation request with no itinerary")
                .path("/makereservation")
                .method("POST")
                .headers(headers)
                .body(buildReservationbadItinerary().toJson())
                .willRespondWith()
                .status(400)
                .headers(headers)
                .body(buildReservationErrorResponse("invalid itinerary").toJson())
                .uponReceiving("a reservation request with no passengers")
                .path("/makereservation")
                .method("POST")
                .headers(headers)
                .body(buildReservationNoPassengers().toJson())
                .willRespondWith()
                .status(400)
                .headers(headers)
                .body(buildReservationErrorResponse("invalid pax list").toJson())
                .toFragment();
    }

    @Test
    @PactVerification("reservation_provider")
    public void runTest() throws IOException {
        BasicHeader header = new BasicHeader("client_name", "uber_app");

        //Green Path
        Assert.assertEquals(new ConsumerClient("http://localhost:8080").postForStatusCode("/makereservation", buildReservationRequest().toJson(), header, ContentType.APPLICATION_JSON), 200);

        //Bad Itinerary
        Assert.assertEquals(new ConsumerClient("http://localhost:8080").postForStatusCode("/makereservation", buildReservationbadItinerary().toJson(), header, ContentType.APPLICATION_JSON), 400);

        //Bad Pax list
        Assert.assertEquals(new ConsumerClient("http://localhost:8080").postForStatusCode("/makereservation", buildReservationNoPassengers().toJson(), header, ContentType.APPLICATION_JSON), 400);

    }
}
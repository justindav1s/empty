package com.ba.captwo.eda.demo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by u760245 on 06/07/2014.
 */

public class Reservation extends ResourceBase{

    static final long serialVersionUID = 1L;

    private ArrayList<Person> persons = new ArrayList<Person>();

    private ArrayList<Flight> itinerary = new ArrayList<Flight>();

    private String bookingId;

    private Error error;

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Flight> getItinerary() {
        return itinerary;
    }

    public void setItinerary(ArrayList<Flight> itinerary) {
        this.itinerary = itinerary;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
}

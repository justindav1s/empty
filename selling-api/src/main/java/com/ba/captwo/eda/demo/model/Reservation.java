package com.ba.captwo.eda.demo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;

/**
 * Created by u760245 on 06/07/2014.
 */

public class Reservation extends ResourceBase{

    static final long serialVersionUID = 1L;

    private Person person;

    private Booking booking;

    private Error error;


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}

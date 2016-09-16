package com.ba.captwo.eda.demo.model;


import java.io.IOException;

/**
 * Created by u760245 on 05/07/2014.
 */
public class Flight extends ResourceBase{
    static final long serialVersionUID = 1L;
    private String flightnum = null;
    private String origin = null;
    private String destination = null;

    public String getFlightnum() {
        return flightnum;
    }

    public void setFlightnum(String flightnum) {
        this.flightnum = flightnum;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}

package com.ba.captwo.eda.demo.model;

/**
 * Created by u760245 on 08/07/2014.
 */
public class FlightEvent extends ResourceBase{
    static final long serialVersionUID = 1L;
    private String flightNum;
    private String event;
    private long timestamp;

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

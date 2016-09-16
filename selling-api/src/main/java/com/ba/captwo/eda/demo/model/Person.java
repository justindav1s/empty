package com.ba.captwo.eda.demo.model;


/**
 * Created by u760245 on 04/07/2014.
 */

public class Person extends ResourceBase {

    static final long serialVersionUID = 1L;

    private int personID = 0;

    private String lastName = null;

    private String firstName = null;

    private String address = null;

    private String city = null;

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}

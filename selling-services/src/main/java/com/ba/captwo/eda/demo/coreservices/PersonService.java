package com.ba.captwo.eda.demo.coreservices;

import com.ba.captwo.eda.demo.model.Person;

import java.util.ArrayList;

/**
 * Created by u760245 on 11/07/2014.
 */
public interface PersonService {

    public Person createPerson(Person p);
    public Person createPerson(String json);
    public Person readPerson(int personid);
    public Person updatePerson(Person p);
    public void deletePerson(int personId);
    public ArrayList<Person> listPersons();

}

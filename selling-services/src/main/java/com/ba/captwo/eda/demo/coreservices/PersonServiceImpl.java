package com.ba.captwo.eda.demo.coreservices;

import com.ba.captwo.eda.demo.db.PersonDAO;
import com.ba.captwo.eda.demo.model.Person;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by u760245 on 11/07/2014.
 */
@Component("PersonCoreServiceBean")
public class PersonServiceImpl implements PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    PersonDAO personDAO;

    @Override
    public Person createPerson(Person p) {
        log.info("createPerson");
        return personDAO.createPerson(p);
    }

    @Override
    public Person createPerson(String json) {

        Person p = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            p = mapper.readValue(json, Person.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createPerson(p);
    }

    @Override
    public Person readPerson(int personid) {
        log.debug("***THREAD : "+ Thread.currentThread().toString());
        log.info("readPerson");
        return personDAO.readPerson(personid);
    }

    @Override
    public Person updatePerson(Person p) {
        log.info("updatePerson");
        return personDAO.updatePerson(p);
    }

    @Override
    public void deletePerson(int personId) {
        log.info("deletePerson");
        personDAO.deletePerson(personId);

    }

    @Override
    public ArrayList<Person> listPersons() {
        log.info("listPersons");
        return personDAO.listPersons();
    }
}

package com.ba.captwo.eda.demo.model;

/**
 * Created by u760245 on 13/07/2014.
 */
public class PersonTest {

    public static void main(String[] args)  {

        PersonTest test = new PersonTest();
        Person p1 = test.buildPerson1();
        Person p2 = test.buildPerson1();
        boolean equal = p1.equals(p2);
        equal = p2.equals(p1);
        p2.setPersonID(1);
        equal = p1.equals(p2);
        p2.setPersonID(0);
        equal = p1.equals(p2);
        p2.setCity("Vienna");
        equal = p1.equals(p2);
        p1.setCity(null);
        p2.setCity(null);
        equal = p1.equals(p2);
    }

    private Person buildPerson1()    {
        Person p = new Person();
        p.setFirstName("Albert");
        p.setLastName("Einstein");
        p.setAddress("Wittelbacher Strasse 13");
        p.setCity("Berlin");
        return p;
    }

    private Person buildPerson2()    {
        Person p = new Person();
        p.setFirstName("Issac");
        p.setLastName("Newton");
        p.setAddress("Woolsthorpe Manor");
        p.setCity("Granthem");
        return p;
    }
}

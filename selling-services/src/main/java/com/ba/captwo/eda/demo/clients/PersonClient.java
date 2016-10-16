package com.ba.captwo.eda.demo.clients;

import com.ba.captwo.eda.demo.model.Person;
import org.apache.http.entity.ContentType;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by justin on 16/10/2016.
 */
public class PersonClient {

    private String uri = "http://localhost:9080/selling/s/person/create";
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity createPerson(Person p, String _uri, Map<String, String> headers)
    {
        if (_uri != null)
            this.uri = _uri;

        restTemplate.setErrorHandler(new PassThroughErrorHandler());

        HttpHeaders httpheaders = new HttpHeaders();
        for (Map.Entry<String, String> entry : headers.entrySet())   {
            ArrayList<String> list = new ArrayList<String>();
            list.add(entry.getValue());
            httpheaders.put(entry.getKey(), list);
        }

        HttpEntity<Person> entity = new HttpEntity<Person>(p, httpheaders);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        String str =  result.getBody();
        System.out.println(str);
        return result;
    }
}

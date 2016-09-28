package com.ba.captwo.eda.demo.resources;

import com.ba.captwo.eda.demo.coreservices.CacheService;
import com.ba.captwo.eda.demo.coreservices.PersonService;
import com.ba.captwo.eda.demo.model.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.MatrixParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by justin on 21/09/2016.
 */
public class CacheResourceImpl implements CacheResource{

    @Autowired
    CacheService cacheService;

    private final Logger log = LoggerFactory.getLogger(CacheResource.class);

    @Override
    public Response addEntry(String val) {

        ResourceRquest rr = new ResourceRquest();
        rr.putNVP("type", "REQUEST");
        rr.putNVP("op", "addEntry");
        rr.putNVP("method", "GET");
        rr.putNVP("val", val);
        log.debug(rr.toJson());

        Response response = null;
        ArrayList<String> list = null;
        try {
            list = cacheService.addEntry(val);
            response = Response.status(Response.Status.OK).entity(list).build();
        }
        catch (Exception e) {
            com.ba.captwo.eda.demo.model.Error err = new Error();
            err.setMessage(e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }

        rr = new ResourceRquest();
        rr.putNVP("type", "RESPONSE");
        rr.putNVP("op", "addEntry");
        rr.putNVP("method", "GET");
        rr.putNVP("response", list.toString());
        log.debug(rr.toJson());
        return response;
    }

    @Override
    public Response listEntries() {
        Response response = null;
        ArrayList<String> list = null;
        try {
            list = cacheService.listEntries();
            response = Response.status(Response.Status.OK).entity(list).build();
        }
        catch (Exception e) {
            com.ba.captwo.eda.demo.model.Error err = new Error();
            err.setMessage(e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }

        ResourceRquest rr = new ResourceRquest();
        rr.putNVP("type", "RESPONSE");
        rr.putNVP("op", "addEntry");
        rr.putNVP("method", "GET");
        rr.putNVP("response", list.toString());
        log.debug(rr.toJson());

        return response;
    }

    @Override
    public Response clearEntries() {
        Response response = null;
        ArrayList<String> list = null;
        try {
            list = cacheService.clearEntries();
            response = Response.status(Response.Status.OK).entity(list).build();
        }
        catch (Exception e) {
            com.ba.captwo.eda.demo.model.Error err = new Error();
            err.setMessage(e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }

        ResourceRquest rr = new ResourceRquest();
        rr.putNVP("type", "RESPONSE");
        rr.putNVP("op", "addEntry");
        rr.putNVP("method", "GET");
        rr.putNVP("response", list.toString());
        log.debug(rr.toJson());

        return response;
    }
}

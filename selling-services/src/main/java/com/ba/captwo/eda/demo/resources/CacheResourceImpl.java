package com.ba.captwo.eda.demo.resources;

import com.ba.captwo.eda.demo.coreservices.CacheService;
import com.ba.captwo.eda.demo.coreservices.PersonService;
import com.ba.captwo.eda.demo.model.Error;
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

    @Override
    public Response addEntry(String val) {
        Response response = null;
        try {
            ArrayList<String> list = cacheService.addEntry(val);
            response = Response.status(Response.Status.OK).entity(list).build();
        }
        catch (Exception e) {
            com.ba.captwo.eda.demo.model.Error err = new Error();
            err.setMessage(e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }

        return response;
    }

    @Override
    public Response listEntries() {
        Response response = null;
        try {
            ArrayList<String> list = cacheService.listEntries();
            response = Response.status(Response.Status.OK).entity(list).build();
        }
        catch (Exception e) {
            com.ba.captwo.eda.demo.model.Error err = new Error();
            err.setMessage(e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }

        return response;
    }

    @Override
    public Response clearEntries() {
        Response response = null;
        try {
            ArrayList<String> list = cacheService.clearEntries();
            response = Response.status(Response.Status.OK).entity(list).build();
        }
        catch (Exception e) {
            com.ba.captwo.eda.demo.model.Error err = new Error();
            err.setMessage(e.getMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(err).build();
        }

        return response;
    }
}

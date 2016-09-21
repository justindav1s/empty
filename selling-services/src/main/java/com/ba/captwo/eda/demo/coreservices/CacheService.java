package com.ba.captwo.eda.demo.coreservices;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by justin on 21/09/2016.
 */
public interface CacheService {

    public ArrayList<String>  addEntry(String val);

    public ArrayList<String>  listEntries();

    public ArrayList<String> clearEntries();
}

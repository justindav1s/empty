package com.ba.captwo.eda.demo.resources;

import com.ba.captwo.eda.demo.model.ResourceBase;

import java.util.HashMap;

/**
 * Created by justin on 28/09/2016.
 */
public class ResourceRquest  extends ResourceBase{

    private HashMap<String, String> map = new HashMap();

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }

    public void putNVP(String name, String value) {
        map.put(name, value);
    }
}

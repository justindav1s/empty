package com.ba.captwo.eda.demo.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by u760245 on 05/07/2014.
 */

public class Error extends ResourceBase{
    static final long serialVersionUID = 1L;
    private String message = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

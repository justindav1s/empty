package com.ba.captwo.eda.demo.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by justin on 19/09/2016.
 */
@Component("RedisConfig")
public class RedisConfig {

    private String hostname;
    private String port;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}

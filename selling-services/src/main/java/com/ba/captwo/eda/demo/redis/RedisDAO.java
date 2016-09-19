package com.ba.captwo.eda.demo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by justin on 18/09/2016.
 */
@Component("RedisDAO")
public class RedisDAO {

    private final Logger log = LoggerFactory.getLogger(RedisDAO.class);

    @Autowired
    public RedisConfig config;



}

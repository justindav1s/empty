package com.ba.captwo.eda.demo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by justin on 18/09/2016.
 */
@Component("DonkeyDAO")
public class DonkeyDAO {

    private final Logger log = LoggerFactory.getLogger(DonkeyDAO.class);

    private RedisTemplate<String, String> redisTemplate = null;
    private BoundListOperations<String, String> listOperations = null;

    @Autowired
    public DonkeyDAO(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.listOperations = this.redisTemplate.boundListOps("cache");
        listOperations.leftPush("Hi !");

    }



    public DonkeyDAO()   {
        log.debug("Redis redisTemplate : "+redisTemplate);
    }

    @PostConstruct
    public void post()   {
        log.debug("Redis redisTemplate : "+redisTemplate);
        listOperations.leftPush("Hi !");

    }


}

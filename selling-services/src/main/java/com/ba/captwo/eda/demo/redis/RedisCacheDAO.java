package com.ba.captwo.eda.demo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundKeyOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

/**
 * Created by justin on 18/09/2016.
 */
@Component("RedisCacheDAO")
public class RedisCacheDAO {

    private final Logger log = LoggerFactory.getLogger(RedisCacheDAO.class);

    private RedisTemplate<String, String> redisTemplate = null;
    private BoundListOperations<String, String> listOperations = null;

    @Autowired
    public RedisCacheDAO(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.listOperations = this.redisTemplate.boundListOps("cache");

    }
    public RedisCacheDAO()   {
        log.debug("Redis redisTemplate : "+redisTemplate);
    }

    @PostConstruct
    public void post()   {
        log.debug("Redis redisTemplate : "+redisTemplate);
    }

    public ArrayList<String> add(String value)  {
        listOperations.leftPush(value);
        ArrayList<String> list = (ArrayList<String>) listOperations.range(0, listOperations.size());
        log.debug("RedisCache : "+list);
        return list;
    }

    public ArrayList<String> list()  {
        ArrayList<String> list = (ArrayList<String>) listOperations.range(0, listOperations.size());
        log.debug("RedisCache : "+list);
        return list;
    }

    public ArrayList<String> clear()  {
        redisTemplate.delete("cache");
        this.listOperations = this.redisTemplate.boundListOps("cache");
        ArrayList<String> list = (ArrayList<String>) listOperations.range(0, listOperations.size());
        log.debug("RedisCache : "+list);
        return list;
    }

}

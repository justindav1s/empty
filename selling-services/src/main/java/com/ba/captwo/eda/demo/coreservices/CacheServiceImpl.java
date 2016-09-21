package com.ba.captwo.eda.demo.coreservices;

import com.ba.captwo.eda.demo.db.PersonDAO;
import com.ba.captwo.eda.demo.redis.RedisCacheDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by justin on 21/09/2016.
 */
@Component("CacheCoreServiceBean")
public class CacheServiceImpl implements CacheService {

    private final Logger log = LoggerFactory.getLogger(CacheService.class);

    @Autowired
    RedisCacheDAO cacheDAO;

    @Override
    public ArrayList<String> addEntry(String val) {
        return cacheDAO.add(val);
    }

    @Override
    public ArrayList<String> listEntries() {
        return cacheDAO.list();
    }

    @Override
    public ArrayList<String> clearEntries() {
        return cacheDAO.clear();
    }
}

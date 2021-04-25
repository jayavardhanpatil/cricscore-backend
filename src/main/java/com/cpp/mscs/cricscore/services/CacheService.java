package com.cpp.mscs.cricscore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 4/24/21
 * Time:  21:25
 */

@Service
public class CacheService {

    @Autowired
    CacheManager cacheManager;

    public void evictAllCaches() {
        cacheManager.getCacheNames().stream()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }
}
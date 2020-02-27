package it.distributedsystems.projectactivity.temperatureservice.util;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CacheEventLogger
 */
public class CacheEventLogger implements CacheEventListener<Object, Object> {

    private static final Logger log = LoggerFactory.getLogger(CacheEventLogger.class);
 
    @Override
    public void onEvent(CacheEvent cacheEvent) {
        log.info("Cache event = {}, Key = {},  Old value = {}, New value = {}", cacheEvent.getType(),
                cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }
}
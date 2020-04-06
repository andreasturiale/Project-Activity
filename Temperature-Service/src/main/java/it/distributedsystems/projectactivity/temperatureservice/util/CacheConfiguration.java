package it.distributedsystems.projectactivity.temperatureservice.util;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.spring.cache.HazelcastCacheManager;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration is needed by Spring boot 
 * to instantiate the CacheManager correctly.
 * 
 * @author Andrea Sturiale
 */
@Configuration
public class CacheConfiguration {
    @Bean
    CacheManager cacheManager() {
        return new HazelcastCacheManager(Hazelcast.newHazelcastInstance());
    }
}
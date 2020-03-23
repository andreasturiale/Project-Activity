package it.distributedsystems.projectactivity.apigateway.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import it.distributedsystems.projectactivity.temperatureservice.model.User;
/**
 * Fallback
 */
@RestController()
public class FallbackTemperatureService {

    @Autowired
    private CacheManager cacheManager;
    
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError,HttpStatus.valueOf(apiError.getStatus()));
    }
    
    private ResponseEntity<Object> buildResponseEntity(User user) {
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/fallback/temperature")
    public ResponseEntity<Object> fallbackPut() {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND.value(), "TEMPERATURE_SERVICE_UNAVAILABLE", "Fallback Method"));
    }

    @GetMapping("/fallback/temperature")
    public ResponseEntity<Object> fallbackGet(ServerWebExchange exchange) {
        Map<String, String> uriVariables = ServerWebExchangeUtils.getUriTemplateVariables(exchange);
        String userId = uriVariables.get("userId");
        Cache cache = cacheManager.getCache("userCache");
        User u=cache.get(Integer.parseInt(userId),User.class);    
        if (u != null)
            return buildResponseEntity(u);
        else 
            return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND.value(), "TEMPERATURE_SERVICE_UNAVAILABLE", "Fallback Method"));
    }

    @PostMapping("/fallback/temperature")
    public ResponseEntity<Object> fallbackPost() {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND.value(), "TEMPERATURE_SERVICE_UNAVAILABLE", "Fallback Method"));
    }

    @DeleteMapping("/fallback/temperature")
    public ResponseEntity<Object> fallbackDelete() {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND.value(), "TEMPERATURE_SERVICE_UNAVAILABLE", "Fallback Method"));
    }

}
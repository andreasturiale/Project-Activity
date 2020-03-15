package it.distributedsystems.projectactivity.apigateway;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Fallback
 */
@RestController()
public class FallbackTemperatureService {

    @PutMapping("/fallback/temperature")
    public ApiError fallbackPut() {
        return new ApiError(HttpStatus.NOT_FOUND.value(), "TEMPERATURE_SERVICE_UNAVAILABLE", "Fallback Method");
    }

    @GetMapping("/fallback/temperature")
    public ApiError fallbackGet() {
        return new ApiError(HttpStatus.NOT_FOUND.value(), "TEMPERATURE_SERVICE_UNAVAILABLE", "Fallback Method");
    }

    @PostMapping("/fallback/temperature")
    public ApiError fallbackPost() {
        return new ApiError(HttpStatus.NOT_FOUND.value(), "TEMPERATURE_SERVICE_UNAVAILABLE", "Fallback Method");
    }

    @DeleteMapping("/fallback/temperature")
    public ApiError fallbackDelete() {
        return new ApiError(HttpStatus.NOT_FOUND.value(), "TEMPERATURE_SERVICE_UNAVAILABLE", "Fallback Method");
    }

}
package it.distributedsystems.projectactivity.apigateway.util;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * This represents a custom error message returned by UserController
 * each time a request doesn't respect any constraint.
 * 
 * @author Andrea Sturiale
 */
public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
 
    private ApiError() {
        timestamp = LocalDateTime.now();
    }
 
	public int getStatus() {
		return this.status;
	}

    public ApiError(int status, String error, String message) {
        this();
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setDebugMessage(String debugMessage) {
        this.message = debugMessage;
    }
    
}
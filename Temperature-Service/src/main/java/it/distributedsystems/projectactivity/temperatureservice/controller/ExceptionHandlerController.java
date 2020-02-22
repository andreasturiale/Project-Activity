package it.distributedsystems.projectactivity.temperatureservice.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import it.distributedsystems.projectactivity.temperatureservice.util.ApiError;

/**
 * ExceptionHandlerController
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError,HttpStatus.valueOf(apiError.getStatus()));
    }

    @ResponseBody
    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<Object> handleException(NoSuchElementException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST.value(), "NOT_FOUND", exception.getMessage()));
    }
}
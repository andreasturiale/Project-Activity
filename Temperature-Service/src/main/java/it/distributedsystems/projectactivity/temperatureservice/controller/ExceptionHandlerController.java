package it.distributedsystems.projectactivity.temperatureservice.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import it.distributedsystems.projectactivity.temperatureservice.exception.ServiceNotAvailableException;
import it.distributedsystems.projectactivity.temperatureservice.exception.UserNotFoundException;
import it.distributedsystems.projectactivity.temperatureservice.util.ApiError;

/**
 * This class catches the exceptions thrown by the UserController giving back 
 * a custom message instead of the default error to the client.
 * 
 * @author Andrea Sturiale
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError,HttpStatus.valueOf(apiError.getStatus()));
    }

    @ResponseBody
    @ExceptionHandler(value = ServiceNotAvailableException.class)
    public ResponseEntity<Object> handleException(ServiceNotAvailableException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "TEMPERATURE_SERVICE_UNAVAILABLE", exception.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handleException(UserNotFoundException exception) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST.value(), "NOT_FOUND", exception.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException exception) {
        //First I find the number of the errors, then I generate the message to send
        StringBuilder message=new StringBuilder("N. of error(s) "+exception.getBindingResult().getAllErrors().size()+": ");
        for (ObjectError error: exception.getBindingResult().getAllErrors()){
            message.append(error.getDefaultMessage()+" - ");
        }
        return buildResponseEntity(
                new ApiError(HttpStatus.BAD_REQUEST.value(), "MALFORMED_INPUT", message.toString()));
    }

    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> handleException(ConstraintViolationException exception) {
        String message;
        // You can distinguish the error message considering the error code of the
        // exception
        switch (exception.getErrorCode()) {
        case 1062:
            message = "DUPLICATION_CONSTRAINT_ERROR";
            break;
        case 1048:
            message = "NOT_NULL_CONSTRAINT_ERROR";
            break;
        default:
            message = "UNDEFINIED";
        }
        return buildResponseEntity(
                new ApiError(HttpStatus.BAD_REQUEST.value(), message, exception.getSQLException().getLocalizedMessage()));
    }
}
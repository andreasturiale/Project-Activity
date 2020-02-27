package it.distributedsystems.projectactivity.temperatureservice.exception;

import javax.persistence.EntityNotFoundException;

/**
 * UserNotFoundException
 */
public class UserNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String message){
        super(message);
    }
    
}
package it.distributedsystems.projectactivity.temperatureservice.exception;

import javax.persistence.EntityNotFoundException;

/**
 * Custom exception thrown when an user isn't found in the database.
 * 
 * @author Andrea Sturiale
 */
public class UserNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String message){
        super(message);
    }
    
}
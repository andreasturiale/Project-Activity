package it.distributedsystems.projectactivity.temperatureservice.exception;

/**
 * NoUserInCacheException
 */
public class NoUserInCacheException extends Exception {

    private static final long serialVersionUID = 1L;

    public NoUserInCacheException(String message){
        super(message);
    }
}
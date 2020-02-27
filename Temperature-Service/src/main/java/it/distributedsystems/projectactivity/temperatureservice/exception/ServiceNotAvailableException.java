package it.distributedsystems.projectactivity.temperatureservice.exception;

/**
 * NoUserInCacheException
 */
public class ServiceNotAvailableException extends Exception {

    private static final long serialVersionUID = 1L;

    public ServiceNotAvailableException(String message){
        super(message);
    }
}
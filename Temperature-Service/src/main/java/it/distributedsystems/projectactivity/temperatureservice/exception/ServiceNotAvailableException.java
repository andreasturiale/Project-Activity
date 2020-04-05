package it.distributedsystems.projectactivity.temperatureservice.exception;

/**
 * Custom exception thrown when the service doesn't work correctly.
 * 
 * @author Andrea Sturiale
 */
public class ServiceNotAvailableException extends Exception {

    private static final long serialVersionUID = 1L;

    public ServiceNotAvailableException(String message){
        super(message);
    }
}
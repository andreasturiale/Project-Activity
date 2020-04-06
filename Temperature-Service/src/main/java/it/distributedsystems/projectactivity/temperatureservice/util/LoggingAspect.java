package it.distributedsystems.projectactivity.temperatureservice.util;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This is the aspect used to log automatically the message receveid by 
 * the sensor through Spring AOP.
 * 
 * @author Andrea Sturiale
 */
@Aspect
@Component
public class LoggingAspect {

    
    private final Logger log = LoggerFactory.getLogger("temperature-message");

    /**
     * Advice that log the parameter of input of TemperatureSensorSink.handle whenever it is executed .
     *
     */
    @Before("execution(void it.distributedsystems.projectactivity.temperatureservice.stream.TemperatureSensorSink.handle(..))")
    public void logBefore(JoinPoint joinPoint) {        
        log.info("Received "+ Arrays.toString(joinPoint.getArgs())+", from worker :" + Thread.currentThread().getName());
    }
    
}
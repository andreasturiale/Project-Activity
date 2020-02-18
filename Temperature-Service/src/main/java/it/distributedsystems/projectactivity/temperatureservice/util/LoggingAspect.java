package it.distributedsystems.projectactivity.temperatureservice.util;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * LoggingAdvice
 */
@Aspect
@Component
public class LoggingAspect {

    
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     */
    @Before("execution(void it.distributedsystems.projectactivity.temperatureservice.stream.TemperatureSensorSink.handle(..))")
    public void logBefore(JoinPoint joinPoint) {        
        log.info("Received "+ Arrays.toString(joinPoint.getArgs())+", from worker :" + Thread.currentThread().getName());
    }
    
}
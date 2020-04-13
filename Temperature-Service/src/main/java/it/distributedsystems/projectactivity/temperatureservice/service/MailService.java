package it.distributedsystems.projectactivity.temperatureservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import it.distributedsystems.projectactivity.temperatureservice.exception.ServiceNotAvailableException;
import it.distributedsystems.projectactivity.temperatureservice.model.TemperatureSensorMessage;
import it.distributedsystems.projectactivity.temperatureservice.model.User;

/**
 * This service is used by TemperatureSensorSink to send 
 * email to the users after receiving a message from the broker.
 * I added a circuit breaker in order to control the requests 
 * sent to Gmail Server.
 * 
 *@author Andrea Sturiale
 */
@Service
@CircuitBreaker(name = "mailService")
public class MailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private UserService userService;

    public void sendEmailToUsers(TemperatureSensorMessage message) {
        //first retrive the list of users to warn from the database through UserService
        List<User> usersToWarn = userService.getUserToWarn(message.getValue());

        //I send the email only if the list is not empty
        if(usersToWarn.size()>0)
            sendEmail(usersToWarn, "Temperature threashold exceeded", "WARNING: ", true, message);
    }

    private void sendEmail(List<User> users, String subject, String text, boolean notified, TemperatureSensorMessage message){
        SimpleMailMessage mail = new SimpleMailMessage();

        //I generate an array of the users mails in order to reach all of them
        //with a single email instead of a create multiple notifications, one for each user
        String[] emails=new String[users.size()];
        users.stream().map(u -> u.getEmail()).collect(Collectors.toList()).toArray(emails);

        //configure and send the email
        mail.setTo(emails);
        mail.setSubject(subject);
        mail.setText(text+message.toString());
        emailSender.send(mail);

        //then I set the "notified" attribute of the users to true avoiding to send other e-mails in the future.
        Cache cache = cacheManager.getCache("userCache");
        for (User u: users){
            u.setNotified(notified);
            //I evict the users in the Cache if they are present because they have been modified.
            cache.evictIfPresent(u.getId());
        }
        userService.saveUsers(users);
    }

    //Method used to test the circuit breaker
    public void failure() throws ServiceNotAvailableException {
        throw new ServiceNotAvailableException("Service momentaneously not available");
    }
}
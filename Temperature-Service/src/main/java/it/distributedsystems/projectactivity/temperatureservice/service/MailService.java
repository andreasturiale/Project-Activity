package it.distributedsystems.projectactivity.temperatureservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import it.distributedsystems.projectactivity.temperatureservice.exception.ServiceNotAvailableException;
import it.distributedsystems.projectactivity.temperatureservice.model.TemperatureSensorMessage;
import it.distributedsystems.projectactivity.temperatureservice.model.User;

/**
 * MailService
 */
@Service
@CircuitBreaker(name = "mailService")
public class MailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private UserService userService;

    public void sendEmailToUsers(TemperatureSensorMessage message){

        List<User> usersToNotify = userService.getUserToWarn(message.getValue());
        List<User> usersAlreadyNotified = userService.getUserToNotify(message.getValue());

        sendEmail(usersToNotify, "Temperature threashold exceeded", "WARNING: ", true, message);
        sendEmail(usersAlreadyNotified, "Temperature returned under the threashold", "INFO: ", false, message);
    }

    private void sendEmail(List<User> users, String subject, String text, boolean notified, TemperatureSensorMessage message){
        SimpleMailMessage mail = new SimpleMailMessage();
        
        for (User u: users){
            mail.setTo(u.getEmail());
            mail.setSubject(subject);
            mail.setText(text+message.toString());
            emailSender.send(mail);
            u.setNotified(notified);
            userService.saveUser(u);
        }
    }

    public void failure() throws ServiceNotAvailableException {
        throw new ServiceNotAvailableException("Service momentaneously not available");
    }
}
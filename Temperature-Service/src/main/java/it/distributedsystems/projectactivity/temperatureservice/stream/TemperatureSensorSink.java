package it.distributedsystems.projectactivity.temperatureservice.stream;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.SubscribableChannel;

import it.distributedsystems.projectactivity.temperatureservice.model.TemperatureSensorMessage;
import it.distributedsystems.projectactivity.temperatureservice.model.User;
import it.distributedsystems.projectactivity.temperatureservice.repository.UserRepository;

/**
 * TemperatureSensorSink
 */
@EnableBinding(TemperatureSensorSink.InputChannel.class)
public class TemperatureSensorSink {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private UserRepository userRepository;

    @StreamListener(InputChannel.SINK)
    public void handle(TemperatureSensorMessage message) {
        List<User> usersToNotify=userRepository.findByThreasholdLessThanEqualAndNotifiedFalse(message.getValue()).orElse(new ArrayList<>());
        List<User> usersAlreadyNotified=userRepository.findByThreasholdGreaterThanAndNotifiedTrue(message.getValue()).orElse(new ArrayList<>());

        SimpleMailMessage mail = new SimpleMailMessage();
        
        for (User u: usersToNotify ){
            mail.setTo(u.getEmail());
            mail.setSubject("Temperature threashold exceeded");
            mail.setText("WARNING: "+message.toString());
            emailSender.send(mail);
            u.setNotified(true);
            userRepository.save(u);
        }
        
        for (User u: usersAlreadyNotified ){
            mail = new SimpleMailMessage();
            mail.setTo(u.getEmail());
            mail.setSubject("Temperature returned under threashold");
            mail.setText("INFO: "+message.toString());
            emailSender.send(mail);
            u.setNotified(false);
            userRepository.save(u);
        }
    }
    
    public interface InputChannel {
        String SINK = "message-sink";
        @Input(SINK)
        SubscribableChannel sink();
    }
}
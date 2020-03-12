package it.distributedsystems.projectactivity.temperatureservice.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;

import it.distributedsystems.projectactivity.temperatureservice.model.TemperatureSensorMessage;
import it.distributedsystems.projectactivity.temperatureservice.service.MailService;

/**
 * TemperatureSensorSink
 */
@EnableBinding(TemperatureSensorSink.InputChannel.class)
public class TemperatureSensorSink {

    @Autowired
    private MailService mailService;
    private int counter=0;
    private static final Logger log = LoggerFactory.getLogger(TemperatureSensorSink.class);

    @StreamListener(InputChannel.SINK)
    public void handle(TemperatureSensorMessage message) {
        this.counter++;
        try {

            mailService.sendEmailToUsers(message);
            
        } catch (Exception e) {
            log.error("Error in sending mail: " + e.getMessage());
        }
    }

    
    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    
    public interface InputChannel {
        String SINK = "message-sink";
        @Input(SINK)
        SubscribableChannel sink();
    }
}
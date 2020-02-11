package it.distributedsystems.projectactivity.temperatureservice.stream;

import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;

/**
 * TemperatureSensorSink
 */
@EnableBinding(TemperatureSensorSink.InputChannel.class)
public class TemperatureSensorSink {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    @StreamListener(InputChannel.SINK)
    public void handle(String message) {
        log.info("new message:" + message + ", from worker :" + Thread.currentThread().getName());       
    }
    
    public interface InputChannel {
        String SINK = "message-sink";
        @Input(SINK)
        SubscribableChannel sink();
    }
}
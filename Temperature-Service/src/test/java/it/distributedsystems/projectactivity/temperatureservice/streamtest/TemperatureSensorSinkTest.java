package it.distributedsystems.projectactivity.temperatureservice.streamtest;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import it.distributedsystems.projectactivity.temperatureservice.model.TemperatureSensorMessage;
import it.distributedsystems.projectactivity.temperatureservice.stream.TemperatureSensorSink;
import it.distributedsystems.projectactivity.temperatureservice.stream.TemperatureSensorSink.InputChannel;
import it.distributedsystems.projectactivity.temperatureservice.usertest.AbstractTest;

/**
 * TemperatureSensorSinkTest
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TemperatureSensorSinkTest extends AbstractTest {

    @Autowired
    private TemperatureSensorSink sink;

    @Autowired
    private InputChannel inputChannel;

    @Test
    public void receivedOneMessage() throws AmqpException, JsonProcessingException {
        inputChannel.sink().send(MessageBuilder.withPayload(new TemperatureSensorMessage("C", 30, "Main Entrance")).build(), 1000);
        assertEquals(1, sink.getCounter());
    }
}
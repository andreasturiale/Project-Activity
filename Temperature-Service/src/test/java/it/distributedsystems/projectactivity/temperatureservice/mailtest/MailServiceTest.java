package it.distributedsystems.projectactivity.temperatureservice.mailtest;

// import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// import java.io.IOException;

// import javax.mail.MessagingException;
// import javax.mail.internet.MimeMessage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
// import it.distributedsystems.projectactivity.temperatureservice.model.TemperatureSensorMessage;
// import it.distributedsystems.projectactivity.temperatureservice.model.User;
import it.distributedsystems.projectactivity.temperatureservice.service.MailService;
// import it.distributedsystems.projectactivity.temperatureservice.service.UserService;
import it.distributedsystems.projectactivity.temperatureservice.usertest.AbstractTest;

/**
 * MailServiceTest
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MailServiceTest extends AbstractTest{

    @Autowired
    private MailService mailService;
    // @Autowired
    // private UserService userService;
    @Rule
    public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);
    //Before test you need to modify application.yml configuration of the mail server
    // @Test
    // public void shouldSendSingleMail() throws MessagingException, IOException {
    //     User user=new User("andrea.sturiale@live.it",29);
    //     userService.saveUser(user);
    //     mailService.sendEmailToUsers(new TemperatureSensorMessage("C", 30, "Main Entrance"));
    //     MimeMessage[] receivedMessages = smtpServerRule.getMessages();
    //     assertEquals(1, receivedMessages.length);
    //     System.out.println(receivedMessages[0].getContent());
    //     userService.deleteUserById(user.getId());
    // }

    @Test
    public void circuitBreakerTest() throws Exception {
       for (int i=0;i<10;i++)
        try {
            mailService.failure();
        } catch (Exception e) {
        }        
       assertThrows(CallNotPermittedException.class, () -> mailService.failure());
    }

}
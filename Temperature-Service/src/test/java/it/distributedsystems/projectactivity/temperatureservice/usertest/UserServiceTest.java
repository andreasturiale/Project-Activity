package it.distributedsystems.projectactivity.temperatureservice.usertest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import it.distributedsystems.projectactivity.temperatureservice.model.User;
import it.distributedsystems.projectactivity.temperatureservice.service.UserService;

/**
 * UserServiceTest
 */
@TestMethodOrder(OrderAnnotation.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest extends AbstractTest {

    @Autowired
    private UserService userService;
    
    @Test
    public void addUserTest() throws Exception {
       User user=userService.saveUser(new User("test@mail.com",29));
       assertEquals(user.getEmail(), "test@mail.com");
       userService.deleteUserById(user.getId());
    }

    @Test
    public void getUserTest() throws Exception {
       User user=new User("test@mail.com",29);
       user=userService.saveUser(user);
       User userObtained=userService.getUserById(user.getId());
       assertEquals(userObtained, user);
       userService.deleteUserById(userObtained.getId());
    }

    @Test
    public void getUsersToNotifyTest() throws Exception {
       User user=new User("test@mail.com",29,true);
       int id=userService.saveUser(user).getId();
       List<User> users=userService.getUserToNotify(27);
       assertEquals(users.get(0).getId(), user.getId());
       userService.deleteUserById(id);
    }

    @Test
    public void getUsersToWarnTest() throws Exception {
       User user=new User("test@mail.com",29);
       int id=userService.saveUser(user).getId(); 
       List<User> users=userService.getUserToWarn(30);
       assertEquals(users.get(0).getId(), id);
       userService.deleteUserById(id);
    }

    @Test
    public void deleteUserTest() throws Exception {
       User user=new User("test@mail.com",29);
       int id=userService.saveUser(user).getId(); 
       userService.deleteUserById(id);
       assertThrows(EntityNotFoundException.class, () -> userService.getUserById(id));
    }

   //  @Test
   //  public void circuitBreakerTest() throws Exception {
   //     for (int i=0;i<10;i++)
   //      try {
   //          userService.failure();
   //      } catch (Exception e) {
   //      }        
   //     assertThrows(CallNotPermittedException.class, () -> userService.failure());
   //  }
}
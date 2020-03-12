package it.distributedsystems.projectactivity.temperatureservice.usertest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import it.distributedsystems.projectactivity.temperatureservice.model.User;
import it.distributedsystems.projectactivity.temperatureservice.service.UserService;

/**
 * UserControllerTest
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest extends AbstractTest {

   @Autowired
   private UserService userService;

   @Test
   public void addUserTest() throws Exception {
      String uri = "/users";
      User user = new User(1,"test@mail.com",29,false);
      String inputJson = super.mapToJson(user);
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
         .contentType(MediaType.APPLICATION_JSON_VALUE)
         .content(inputJson)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      String content = mvcResult.getResponse().getContentAsString();
      user=mapFromJson(content, User.class);
      assertEquals(200, status);
      userService.deleteUserById(user.getId());
   }

   @Test
   public void duplicateEntryTest() throws Exception {
      String uri = "/users";
      User user = new User(1,"test@mail.com",29,false);
      user=this.userService.saveUser(user);
      String inputJson = super.mapToJson(new User("test@mail.com",29));
      System.out.println(inputJson);
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
         .contentType(MediaType.APPLICATION_JSON_VALUE)
         .content(inputJson)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(400, status);
      userService.deleteUserById(user.getId());
   }

   @Test
   public void validationInputTest() throws Exception {
      String uri = "/users";
      User user = new User();
      user.setEmail("testmail.com");
      user.setThreashold(29);
      String inputJson = super.mapToJson(user);
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
         .contentType(MediaType.APPLICATION_JSON_VALUE)
         .content(inputJson)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(400, status);
   }

   @Test
   public void updateUserTest() throws Exception {
      String uri = "/users";
      User user = new User();
      user.setId(1);
      user.setEmail("test@mail.com");
      user.setThreashold(29);
      user=userService.saveUser(user);
      user.setEmail("update@mail.com");
      String inputJson = super.mapToJson(user);
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
         .contentType(MediaType.APPLICATION_JSON_VALUE)
         .content(inputJson)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);

      userService.deleteUserById(user.getId());
   }

   @Test
   public void getUserTest() throws Exception {
      String uri = "/users/";
      User user = new User();
      user.setEmail("test@mail.com");
      user.setThreashold(29);
      int id=userService.saveUser(user).getId();
      uri=uri+id;
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
      String content = mvcResult.getResponse().getContentAsString();
      user=mapFromJson(content, User.class);
      assertEquals(user.getId(),id);
      userService.deleteUserById(user.getId());
   }

   @Test
   public void deleteUserTest() throws Exception {
      String uri = "/users/";
      User user = new User();
      user.setEmail("test@mail.com");
      user.setThreashold(29);
      int id=userService.saveUser(user).getId();
      uri=uri+id;
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
      String content = mvcResult.getResponse().getContentAsString();
      assertEquals(content, "User deleted successfully");
   }
}
package it.distributedsystems.projectactivity.temperatureservice.usertest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;

/**
 * AbstractMvcTest consists in the abstract class from wich one can generate 
 * tests for the other components
 */


public class AbstractTest {

   protected MockMvc mvc;
   @Autowired
   private WebApplicationContext webApplicationContext;

   @Before
   public void setup() {
       mvc = webAppContextSetup(webApplicationContext).build();
   }
   public String mapToJson(final Object obj) throws JsonProcessingException {
      final ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(obj);
   }

   protected <T> T mapFromJson(String json, Class<T> clazz)
      throws JsonParseException, JsonMappingException, IOException {
      
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(json, clazz);
   }
}
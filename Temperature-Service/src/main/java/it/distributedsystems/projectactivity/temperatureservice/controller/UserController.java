package it.distributedsystems.projectactivity.temperatureservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.distributedsystems.projectactivity.temperatureservice.model.User;
import it.distributedsystems.projectactivity.temperatureservice.service.UserService;

/**
 * This rest controller responds to the Http requests done to retrive or update
 * user entities. It validates the input through the annotation @Valid and specifies 
 * the Url to request through @RequestMapping. Each call is forwarded to
 * the UserService.
 * 
 * @author Andrea Sturiale
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/temperature/users/{userId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    @RequestMapping(value = "/temperature/users/{userId}", method = RequestMethod.DELETE)
    public String deleteUserById(@PathVariable int userId) {
        userService.deleteUserById(userId);
        return "User deleted successfully";
    }

    @RequestMapping(value = "/temperature/users/{userId}", method = RequestMethod.PUT)
    public User updateUserById(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    @RequestMapping(value = "/temperature/users", method = RequestMethod.POST)
    public User addUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }
    
    @RequestMapping(value = "/temperature/users/failure", method = RequestMethod.GET)
    public void failure() throws Throwable {
        userService.failure();
    }
}
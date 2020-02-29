package it.distributedsystems.projectactivity.temperatureservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.distributedsystems.projectactivity.temperatureservice.model.User;
import it.distributedsystems.projectactivity.temperatureservice.service.UserService;

/**
 * UserController
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET, params = "userId")
    public User getUserById(@RequestParam int userId) {
        return userService.getUserById(userId);
    }

    @RequestMapping(value = "/users/delete", method = RequestMethod.GET, params = "userId")
    public void deleteUserById(@RequestParam int userId) {
        userService.deleteUserById(userId);
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public User updateUserById(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public User addUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }
    
}
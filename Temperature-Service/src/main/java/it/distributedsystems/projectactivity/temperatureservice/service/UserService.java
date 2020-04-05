package it.distributedsystems.projectactivity.temperatureservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import it.distributedsystems.projectactivity.temperatureservice.exception.ServiceNotAvailableException;
import it.distributedsystems.projectactivity.temperatureservice.exception.UserNotFoundException;
import it.distributedsystems.projectactivity.temperatureservice.model.User;
import it.distributedsystems.projectactivity.temperatureservice.repository.UserRepository;

/**
 * This component gives the access to the database through UserRepository. 
 * It's used by UserController to manage users entities and by MailService to retrive the emails.
 * I added a cache level to  getUserById method avoiding to access the database each time for 
 * read operations increasing the performance of the system.
 * Besides there is a circuit breaker and retry module to control the requests to Mysql store.
 * 
 * @author Andrea Sturiale
 */
@CircuitBreaker(name = "userService")
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Retry(name = "retryService", fallbackMethod = "fallbackAddUser") 
    @CachePut(value = "userCache", key = "#user.id")
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    private User fallbackAddUser(User user,Throwable e)  throws Throwable {
        throw new ServiceNotAvailableException("Service temporarly unavailable");
    }

    private User fallbackAddUser(User user, DataIntegrityViolationException e)  throws Throwable {
        throw e;
    }

    @Transactional
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetUser")
    @Cacheable(value = "userCache", key = "#id")
    public User getUserById(int id) {
        User user=userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Not found any user with id: "+id));
        return user;
    }

    private User fallbackGetUser(int id, Throwable e) throws Throwable {
        throw new ServiceNotAvailableException("Service temporarly unavailable");
    }

    private User fallbackGetUser(int id, UserNotFoundException e) throws Throwable {
        throw e;
    }

    @Transactional
    @Retry(name = "retryService", fallbackMethod = "fallbackDeleteUser")
    @CacheEvict(value="userCache",key="#id", beforeInvocation = false)
    public void deleteUserById(int id) {
        try {
            userRepository.deleteById(id);     
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("Not found any user with id: "+id);
        }
    }

    private void fallbackDeleteUser(int id,Throwable e)  throws Throwable {
        throw new ServiceNotAvailableException("Service temporarly unavailable");
   }


    private void fallbackDeleteUser(int id,UserNotFoundException e)  throws Throwable {
        throw e;
    }

    //Method used only by MailService to find thenlist of users to notify. 
    //if there isn't any user, an empty list is returned
    @Transactional
    @Retry(name = "retryService")
    public List<User> getUserToWarn(float threashold){
        return userRepository.findByThreasholdLessThanEqualAndNotifiedFalse(threashold).orElse(new ArrayList<>());
    }

    //Method used only to test the circuit breaker
    public void failure() throws ServiceNotAvailableException {
        throw new ServiceNotAvailableException("Service temporarly unavailable");
    }
}
package it.distributedsystems.projectactivity.temperatureservice.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
 * UserService
 */
@CircuitBreaker(name = "userService")
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CacheManager cacheManager;

    @Transactional
    @Retry(name = "userService", fallbackMethod = "fallbackAddUser") 
    @CachePut(value = "userCache", key = "#user.id")
    public User saveUser(User user) {
        user.setLastUpdate(Timestamp.from(Instant.now()));
        return userRepository.save(user);
    }

    private User fallbackAddUser(User user,Throwable e)  throws Throwable {
        throw new ServiceNotAvailableException("Service momentaneously not available");
    }

    private User fallbackAddUser(User user, DataIntegrityViolationException e)  throws Throwable {
        throw e;
    }

    @Transactional(readOnly = true)
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetUser")
    @CachePut(value = "userCache", key = "#id")
    public User getUserById(int id) {
        User user=userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Not found any user with id: "+id));
        user.setLastUpdate(Timestamp.from(Instant.now()));
        return user;
    }

    private User fallbackGetUser(int id, Throwable e) throws Throwable {
        Cache cache = cacheManager.getCache("userCache");
        User u=cache.get(id,User.class);    
        if (u != null)
            return u;
        else 
            throw new ServiceNotAvailableException("Service momentaneously not available");
    }

    private User fallbackGetUser(int id, UserNotFoundException e) throws Throwable {
        throw e;
    }

    @Transactional
    @Retry(name = "userService", fallbackMethod = "fallbackDeleteUser")
    @CacheEvict(value="userCache",key="#id")
    public void deleteUserById(int id) {
        try {
            userRepository.deleteById(id);     
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("Not found any user with id: "+id);
        }
    }

    private void fallbackDeleteUser(int id,Throwable e)  throws Throwable {
        throw new ServiceNotAvailableException("Service momentaneously not available");
    }

    private void fallbackDeleteUser(int id,UserNotFoundException e)  throws Throwable {
        throw e;
    }

    @Transactional(readOnly = true)
    @Retry(name = "userService")
    public List<User> getUserToWarn(float threashold){
        return userRepository.findByThreasholdLessThanEqualAndNotifiedFalse(threashold).orElse(new ArrayList<>());
    }

    @Transactional(readOnly = true)
    @Retry(name = "userService")
    public List<User> getUserToNotify(float threashold){
        return userRepository.findByThreasholdGreaterThanAndNotifiedTrue(threashold).orElse(new ArrayList<>());
    }

    public void failure() throws ServiceNotAvailableException {
        throw new ServiceNotAvailableException("Service momentaneously not available");
    }
}
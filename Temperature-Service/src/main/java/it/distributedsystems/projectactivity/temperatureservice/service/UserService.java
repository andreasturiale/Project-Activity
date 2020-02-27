package it.distributedsystems.projectactivity.temperatureservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import it.distributedsystems.projectactivity.temperatureservice.exception.NoUserInCacheException;
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

    @CachePut(value = "userCache", key = "#user.id")
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // public User getUserByEmail(String email){
    // return userRepository.findByEmail(email).get();
    // }

    @CircuitBreaker(name = "userService", fallbackMethod = "fallback")
    @CachePut(value = "userCache", key = "#id")
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Not found any user with id: "+id));
    }

    @CacheEvict(value="userCache",key="#id")
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    // public void deleteUserByEmail(String email) {
    //     userRepository.deleteByEmail(email);
    // }

    public User fallback(int id, Throwable e) throws Throwable {
        if (e instanceof UserNotFoundException)
            throw e;

        Cache cache = cacheManager.getCache("userCache");
        User u=cache.get(id,User.class);    
        if (u != null)
            return u;
        else 
            throw new NoUserInCacheException("Not found any user in cache with id: "+id);
    }
}
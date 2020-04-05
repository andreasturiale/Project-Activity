package it.distributedsystems.projectactivity.temperatureservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.distributedsystems.projectactivity.temperatureservice.model.User;

/**
 * Interface that is automatically implemented by Spring Boot. 
 * In addition to the classic method like deleteById or findById 
 * another method was defined to find the list of users to notify.
 * 
 * @author Andrea Sturiale
 */
public interface UserRepository extends JpaRepository<User, Integer>{
    //This method will be converted in the corrisponding SQL query
    Optional<List<User>> findByThreasholdLessThanEqualAndNotifiedFalse(float threashold);
}
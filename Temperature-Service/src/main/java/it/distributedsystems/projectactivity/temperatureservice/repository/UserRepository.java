package it.distributedsystems.projectactivity.temperatureservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.distributedsystems.projectactivity.temperatureservice.model.User;

/**
 * UserRepository
 */
public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findByEmail(String email);
    Optional<User> findById(int id);
    Optional<List<User>> findByThreasholdLessThanEqualAndNotifiedFalse(float threashold);
    Optional<List<User>> findByThreasholdGreaterThanAndNotifiedTrue(float threashold);

}
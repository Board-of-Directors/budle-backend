package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.budle.dao.User;

import java.util.Optional;

/**
 * Repository, that connects user models with database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Checking existing of user by provided phone number.
     *
     * @param phoneNumber with that number we search user.
     * @return true - if user exists, false - otherwise.
     */

    Boolean existsByPhoneNumber(String phoneNumber);

    /**
     * Searching for user details by provided username.
     *
     * @param username with that we search user.
     * @return UserDetails object connected with this user.
     */

    UserDetails findByUsername(String username);

    /**
     * Checking existing of user by provided username.
     *
     * @param username - with that username we search user.
     * @return true - if user exists, false - otherwise.
     */

    boolean existsByUsername(String username);

    Optional<User> findByPhoneNumber(String phoneNumber);
}

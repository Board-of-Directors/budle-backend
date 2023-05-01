package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.pak.budle.dao.Code;

import java.util.Optional;

/**
 * Repository, that connects code models with database.
 */
public interface CodeRepository extends JpaRepository<Code, Long> {

    /**
     * Find code entity, by string code representation and phone number.
     *
     * @param phoneNumber that we search in database.
     * @param code        that we search in database.
     * @return optional code entity.
     */

    Optional<Code> findByPhoneNumberAndCode(String phoneNumber, String code);
}

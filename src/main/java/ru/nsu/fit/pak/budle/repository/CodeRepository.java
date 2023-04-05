package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.fit.pak.budle.dao.Code;

/**
 * Repository, that connects code models with database.
 */
public interface CodeRepository extends CrudRepository<Code, Long> {

    /**
     * Indicates, if code number exist by phone number and code.
     *
     * @param phoneNumber that we search in database.
     * @param code        that we search in database.
     * @return true - if exists, false - otherwise.
     */
    boolean existsByPhoneNumberAndCode(String phoneNumber, String code);
}

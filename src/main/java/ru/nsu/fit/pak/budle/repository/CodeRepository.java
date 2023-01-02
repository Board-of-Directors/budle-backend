package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.fit.pak.budle.dao.Code;

public interface CodeRepository extends CrudRepository<Code, Long> {
    boolean existsByPhoneNumberAndCode(String phoneNumber, String code);
}

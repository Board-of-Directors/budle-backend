package ru.nsu.fit.pak.Budle.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.fit.pak.Budle.dao.Code;

public interface CodeRepository extends CrudRepository<Code, Long> {
    public boolean existsByPhoneNumberAndCode(String phoneNumber, String code);
}

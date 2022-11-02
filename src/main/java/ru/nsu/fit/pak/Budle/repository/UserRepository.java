package ru.nsu.fit.pak.Budle.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.Budle.dao.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByPhoneNumber(String phoneNumber);
}

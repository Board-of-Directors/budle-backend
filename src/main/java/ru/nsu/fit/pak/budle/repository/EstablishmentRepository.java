package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.budle.dao.Establishment;
import ru.nsu.fit.pak.budle.dao.User;

import java.util.List;

@Repository
public interface EstablishmentRepository extends CrudRepository<Establishment, Long> {
    List<Establishment> findByOwner(User owner);
    Establishment getEstablishmentById(Long id);

    List<Establishment> findByCategory(String category);
}

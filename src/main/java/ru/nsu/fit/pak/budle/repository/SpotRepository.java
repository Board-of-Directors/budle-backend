package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.budle.dao.Spot;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import java.util.List;

@Repository
public interface SpotRepository extends CrudRepository<Spot, Long> {
    List<Spot> findByEstablishment(Establishment establishment);
}

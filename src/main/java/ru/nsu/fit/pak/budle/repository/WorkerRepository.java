package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.budle.dao.Worker;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, Long> {
    Optional<Worker> findWorkerById(Long id);

    List<Worker> findByEstablishments(Establishment establishment);

}

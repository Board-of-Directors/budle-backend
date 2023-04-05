package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.budle.dao.Worker;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import java.util.List;
import java.util.Optional;

/**
 * Repository, that connects worker models with database.
 */
@Repository
public interface WorkerRepository extends CrudRepository<Worker, Long> {
    /**
     * Try to find worker by id.
     *
     * @param id of worker, that we search for.
     * @return optinal worker.
     */
    Optional<Worker> findWorkerById(Long id);

    /**
     * Searching workers by establishments.
     *
     * @param establishment - in what establishments worker works.
     * @return list of all workers.
     */

    List<Worker> findByEstablishments(Establishment establishment);

}

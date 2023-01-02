package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.budle.dao.Worker;

import java.util.List;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, Long> {
    List<Worker> findWorkerById(Long id);
}

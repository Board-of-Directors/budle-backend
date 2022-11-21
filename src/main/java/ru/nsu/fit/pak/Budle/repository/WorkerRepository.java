package ru.nsu.fit.pak.Budle.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.Budle.dao.Worker;

import java.util.List;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, Long> {
    List<Worker> findWorkerByWorkerType(String workerType);
}

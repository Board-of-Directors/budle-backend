package ru.nsu.fit.pak.budle.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dto.WorkerDto;

import java.util.List;

/**
 * Service that responsible for workers.
 */
@Service
public interface WorkerService {

    /**
     * Function that gets workers those work in provided establishment.
     *
     * @param establishmentId from what establishment we get workers.
     * @return list of worker dto.
     */
    List<WorkerDto> getWorkers(Long establishmentId);

    /**
     * Deleted worker from current establishment.
     *
     * @param workerId what worker we need to delete.
     */
    void deleteWorker(Long workerId, Long establishmentId);

    void createWorker(String phoneNumber, Long establishmentId);
}

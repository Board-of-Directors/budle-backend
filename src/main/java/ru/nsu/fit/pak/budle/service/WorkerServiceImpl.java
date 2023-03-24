package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Worker;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.WorkerDto;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentNotFoundException;
import ru.nsu.fit.pak.budle.exceptions.WorkerNotFoundException;
import ru.nsu.fit.pak.budle.mapper.WorkerMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.WorkerRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository workerRepository;
    private final EstablishmentRepository establishmentRepository;

    private final WorkerMapper workerMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public List<WorkerDto> getWorkers(Long establishmentId) {
        logger.info("Getting workers list");
        logger.debug("Establishment ID: " + establishmentId);
        Establishment establishment = establishmentRepository.findById(establishmentId)
                .orElseThrow(() -> new EstablishmentNotFoundException(establishmentId));
        return workerRepository
                .findByEstablishments(establishment)
                .stream()
                .map(workerMapper::modelToDto)
                .toList();
    }

    @Override
    public void deleteWorker(Long workerId) {
        logger.info("Deleting worker");
        logger.debug("Worker with ID: " + workerId);
        Worker worker = workerRepository.findWorkerById(workerId)
                .orElseThrow(() -> new WorkerNotFoundException(workerId));
        workerRepository.delete(worker);
    }

}

package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
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


    @Override
    public List<WorkerDto> getWorkers(Long establishmentId) {
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
        Worker worker = workerRepository.findWorkerById(workerId)
                .orElseThrow(() -> new WorkerNotFoundException(workerId));
        workerRepository.delete(worker);
    }

}

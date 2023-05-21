package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.Worker;
import ru.nsu.fit.pak.budle.dao.WorkerStatus;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.WorkerDto;
import ru.nsu.fit.pak.budle.exceptions.WorkerNotFoundException;
import ru.nsu.fit.pak.budle.mapper.WorkerMapper;
import ru.nsu.fit.pak.budle.repository.WorkerRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Slf4j
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository workerRepository;
    private final EstablishmentService establishmentService;

    private final UserService userService;

    private final WorkerMapper workerMapper;



    @Override
    public List<WorkerDto> getWorkers(Long establishmentId) {
        log.info("Getting workers list");
        log.info("Establishment ID: " + establishmentId);
        Establishment establishment = establishmentService.getEstablishmentById(establishmentId);
        List<Worker> workerList = workerRepository.findByEstablishments(establishment);
        return workerMapper.toDtoList(workerList);
    }

    @Override
    public void deleteWorker(Long workerId, Long establishmentId) {
        log.info("Deleting worker");
        log.info("Worker with ID: " + workerId);
        Establishment establishment = establishmentService.getEstablishmentById(establishmentId);
        Worker worker = workerRepository
                .findByEstablishmentAndWorkerId(establishment, workerId)
                .orElseThrow(() -> new WorkerNotFoundException(workerId));
        worker.getEstablishments()
                .removeIf((x) -> Objects.equals(x.getId(), establishment.getId()));
        workerRepository.save(worker);
    }

    @Override
    public void createWorker(String phoneNumber, Long establishmentId) {
        User user = userService.findByPhoneNumber(phoneNumber);
        Optional<Worker> workerOptional = workerRepository.findByUser(user);
        Establishment establishment = establishmentService.getEstablishmentById(establishmentId);
        Worker worker;
        if (workerOptional.isPresent()) {
            worker = workerOptional.get();
            worker.getEstablishments().add(establishment);
        } else {
            worker = new Worker();
            worker.setStatus(WorkerStatus.absent);
            worker.setEstablishments(Stream.of(establishment).collect(Collectors.toSet()));
            worker.setUser(user);
        }
        workerRepository.save(worker);
    }


}

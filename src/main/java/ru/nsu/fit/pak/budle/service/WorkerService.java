package ru.nsu.fit.pak.budle.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dto.WorkerDto;

import java.util.List;

@Service
public interface WorkerService {

    List<WorkerDto> getWorkers(Long establishmentId);

    void deleteWorker(Long workerId);
}

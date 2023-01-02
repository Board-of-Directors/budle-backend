package ru.nsu.fit.pak.budle.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dto.WorkerDto;

@Service
public interface WorkerService {

    WorkerDto getWorkerById(Long id);
}

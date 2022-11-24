package ru.nsu.fit.pak.Budle.service;

import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.Budle.dto.WorkerDto;

@Service
public interface WorkerService {

    WorkerDto getWorkerById(Long id);
}

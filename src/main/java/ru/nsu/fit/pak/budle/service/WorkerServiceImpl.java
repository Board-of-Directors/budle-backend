package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dto.WorkerDto;
import ru.nsu.fit.pak.budle.exceptions.WorkerNotFoundException;
import ru.nsu.fit.pak.budle.mapper.WorkerMapper;
import ru.nsu.fit.pak.budle.repository.WorkerRepository;


@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository workerRepository;

    private final WorkerMapper workerMapper;

    @Override
    public WorkerDto getWorkerById(Long id) {
        return workerMapper.modelToDto(workerRepository.findWorkerById(id)
                .orElseThrow(() -> new WorkerNotFoundException("Worker with id " + id + " not found")));
    }

}

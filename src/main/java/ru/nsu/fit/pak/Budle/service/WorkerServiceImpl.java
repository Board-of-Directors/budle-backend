package ru.nsu.fit.pak.Budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.Budle.dto.WorkerDto;
import ru.nsu.fit.pak.Budle.mapper.WorkerMapper;
import ru.nsu.fit.pak.Budle.repository.WorkerRepository;


@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository workerRepository;

    private final WorkerMapper workerMapper;

    @Override
    public WorkerDto getWorkerById(Long id) {
        return workerMapper.modelToDto(workerRepository.findWorkerById(id).get(0));
    }

}

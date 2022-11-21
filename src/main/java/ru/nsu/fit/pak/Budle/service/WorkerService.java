package ru.nsu.fit.pak.Budle.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.fit.pak.Budle.dao.Worker;
import ru.nsu.fit.pak.Budle.dto.WorkerDto;
import ru.nsu.fit.pak.Budle.mapper.WorkerMapper;
import ru.nsu.fit.pak.Budle.repository.WorkerRepository;

import java.util.List;

public class WorkerService implements WorkerServiceInterface{
    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private WorkerMapper workerMapper;

    @Override
    public WorkerDto getWorkerById(Long id) {
        return workerMapper.modelToDto(workerRepository.findWorkerById(id).get(0));
    }

}

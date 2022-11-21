package ru.nsu.fit.pak.Budle.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.fit.pak.Budle.dao.Worker;
import ru.nsu.fit.pak.Budle.repository.WorkerRepository;

import java.util.List;

public class WorkerService implements WorkerServiceInterface{
    @Autowired
    private WorkerRepository workerRepository;


    @Override
    public List<Worker> getWorkers() {
        return ;
    }
}

package ru.nsu.fit.pak.Budle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.Budle.dao.Worker;
import ru.nsu.fit.pak.Budle.repository.WorkerRepository;

import java.util.List;

@Service
public interface WorkerServiceInterface {

    List<Worker> getWorkers();
}

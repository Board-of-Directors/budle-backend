package ru.nsu.fit.pak.Budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.pak.Budle.dto.WorkerDto;
import ru.nsu.fit.pak.Budle.service.WorkerService;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class WorkerController {
    @Autowired
    private WorkerService workerService;

    @GetMapping(value = "/worker/{id}")
    public WorkerDto getWorker(@PathVariable("id") Long id) {
        return workerService.getWorkerById(id);
    }
}

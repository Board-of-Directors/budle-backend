package ru.nsu.fit.pak.Budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.pak.Budle.dto.WorkerDto;
import ru.nsu.fit.pak.Budle.service.WorkerServiceImpl;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class WorkerController {
    @Autowired
    private WorkerServiceImpl workerService;

    @GetMapping(value = "/worker")
    public WorkerDto getWorker(@RequestParam Long id) {
        return workerService.getWorkerById(id);
    }
}

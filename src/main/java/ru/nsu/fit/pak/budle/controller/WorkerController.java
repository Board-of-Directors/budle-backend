package ru.nsu.fit.pak.budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.pak.budle.dto.WorkerDto;
import ru.nsu.fit.pak.budle.service.WorkerServiceImpl;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class WorkerController {
    private final WorkerServiceImpl workerService;

    @GetMapping(value = "/worker")
    public WorkerDto getWorker(@RequestParam Long id) {
        return workerService.getWorkerById(id);
    }
}

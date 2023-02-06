package ru.nsu.fit.pak.budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.pak.budle.dto.WorkerDto;
import ru.nsu.fit.pak.budle.service.WorkerServiceImpl;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@RequestMapping(value = "/worker", produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkerController {
    private final WorkerServiceImpl workerService;

    @GetMapping
    public WorkerDto getWorker(@RequestParam Long id) {
        return workerService.getWorkerById(id);
    }
}

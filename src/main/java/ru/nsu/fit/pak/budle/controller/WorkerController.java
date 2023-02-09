package ru.nsu.fit.pak.budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.pak.budle.dto.WorkerDto;
import ru.nsu.fit.pak.budle.service.WorkerServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@RequestMapping(value = "/worker", produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkerController {
    private final WorkerServiceImpl workerService;

    @GetMapping
    public List<WorkerDto> getWorkers(@RequestParam Long establishmentId) {
        return workerService.getWorkers(establishmentId);
    }

    @DeleteMapping
    public void delete(@RequestParam Long workerId) {
        workerService.deleteWorker(workerId);
    }
}

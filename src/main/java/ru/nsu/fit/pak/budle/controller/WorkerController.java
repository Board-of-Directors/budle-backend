package ru.nsu.fit.pak.budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.pak.budle.dto.WorkerDto;
import ru.nsu.fit.pak.budle.service.WorkerServiceImpl;

import java.util.List;

/**
 * Class, that represents worker controller of our system.
 * Main endpoint = "URL:PORT/worker"
 */
@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@RequestMapping(value = "/worker", produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkerController {
    private final WorkerServiceImpl workerService;

    /**
     * Get request, that returns all workers in current establishment.
     *
     * @param establishmentId from what establishment we need to get workers.
     * @return List of worker information.
     */
    @GetMapping
    public List<WorkerDto> getWorkers(@RequestParam Long establishmentId) {
        return workerService.getWorkers(establishmentId);
    }

    /**
     * Delete request, delete worker from current establishment.
     *
     * @param workerId - id of worker what we want to delete.
     */
    @DeleteMapping
    public void delete(@RequestParam Long workerId,
                       @RequestParam Long establishmentId) {
        workerService.deleteWorker(workerId, establishmentId);
    }

    @PutMapping
    public void invite(@RequestParam String phoneNumber,
                       @RequestParam Long establishmentId) {
        workerService.createWorker(phoneNumber, establishmentId);
    }
}

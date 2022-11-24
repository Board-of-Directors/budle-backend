package ru.nsu.fit.pak.Budle.controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.nsu.fit.pak.Budle.dto.WorkerDto;
import ru.nsu.fit.pak.Budle.service.WorkerService;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class WorkerContoller {
    @Autowired
    WorkerService workerService;

    @GetMapping(value = "/worker/{id}")
    public ResponseEntity<WorkerDto> getWorker(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(workerService.getWorkerById(id));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}

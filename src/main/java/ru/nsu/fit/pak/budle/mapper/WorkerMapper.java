package ru.nsu.fit.pak.budle.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Worker;
import ru.nsu.fit.pak.budle.dto.WorkerDto;

/**
 * Class, that represent mapper for worker class.
 */
@Component
@RequiredArgsConstructor
public class WorkerMapper {

    private final ModelMapper modelMapper;

    /**
     * Map worker model to worker dto.
     *
     * @param worker model object.
     * @return worker dto object.
     */
    public WorkerDto modelToDto(Worker worker) {
        WorkerDto workerDto = modelMapper.map(worker, WorkerDto.class);
        workerDto.setOnWork(worker.getStatus().value.equals(1));
        return workerDto;
    }
}

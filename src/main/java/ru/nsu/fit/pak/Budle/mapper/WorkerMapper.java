package ru.nsu.fit.pak.Budle.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.Budle.dao.Worker;
import ru.nsu.fit.pak.Budle.dto.UserDto;
import ru.nsu.fit.pak.Budle.dto.WorkerDto;

@Component
public class WorkerMapper {
    public WorkerDto modelToDto(Worker worker) {
        UserMapper userMapper = new UserMapper();
        EstablishmentMapper establishmentMapper = new EstablishmentMapper();
        WorkerDto dto = new WorkerDto();
        dto.setId(worker.getId());
        dto.setOnWork(worker.getOnWork());
        dto.setWorkerType(worker.getWorkerType());
        dto.setEstablishments(worker.getEstablishments());
        return dto;
    }
}

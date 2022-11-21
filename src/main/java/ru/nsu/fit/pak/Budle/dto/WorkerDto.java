package ru.nsu.fit.pak.Budle.dto;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.pak.Budle.dao.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Getter
@Setter
public class WorkerDto {
    @Null
    private Long id;
    @NotNull
    private Boolean onWork;
    @NotNull
    private String workerType;
    @NotNull
    private UserDto user;
    private List<EstablishmentDto> establishments;
}

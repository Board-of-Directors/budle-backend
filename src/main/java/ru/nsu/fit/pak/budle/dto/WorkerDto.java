package ru.nsu.fit.pak.budle.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Data
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

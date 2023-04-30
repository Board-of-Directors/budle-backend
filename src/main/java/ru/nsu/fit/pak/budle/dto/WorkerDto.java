package ru.nsu.fit.pak.budle.dto;

import lombok.Data;
import ru.nsu.fit.pak.budle.dto.response.ResponseUserDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class WorkerDto {
    @Null
    private Long id;
    @NotNull
    private Boolean onWork;
    @NotNull
    private ResponseUserDto user;
}

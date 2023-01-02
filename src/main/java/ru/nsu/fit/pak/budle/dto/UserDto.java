package ru.nsu.fit.pak.budle.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class UserDto {
    @Null
    private Long id;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
    private String phoneNumber;
}

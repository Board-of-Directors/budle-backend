package ru.nsu.fit.pak.Budle.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
public class EstablishmentDto {
    @Null
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String address;
    @NotNull
    private UserDto owner;

    private boolean hasCardPayment;

    private boolean hasMap;
}

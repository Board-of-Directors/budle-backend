package ru.nsu.fit.pak.budle.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
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

    private String category;
}

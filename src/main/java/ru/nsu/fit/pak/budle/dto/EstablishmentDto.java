package ru.nsu.fit.pak.budle.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
public class EstablishmentDto {
    @Null
    private Long id;
    @NotNull
    @Size(max = 200)
    private String name;
    @NotNull
    @Size(max = 1000)
    private String description;
    @NotNull
    @Size(max = 200)
    private String address;
    @NotNull
    private UserDto owner;
    @NotNull
    private boolean hasCardPayment;
    @NotNull
    private boolean hasMap;
    @NotNull
    private String category;
    @NotNull
    private String image;
}

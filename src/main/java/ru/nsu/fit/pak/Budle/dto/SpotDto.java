package ru.nsu.fit.pak.Budle.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotDto {
    private Long id;
    private String tags;
    private String status;
    private EstablishmentDto establishment;
}

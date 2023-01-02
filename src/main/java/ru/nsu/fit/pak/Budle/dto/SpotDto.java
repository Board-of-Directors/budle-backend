package ru.nsu.fit.pak.Budle.dto;


import lombok.Data;

@Data
public class SpotDto {
    private Long id;
    private String tags;
    private String status;
    private EstablishmentDto establishment;
}

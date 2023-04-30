package ru.nsu.fit.pak.budle.dto;


import lombok.Data;
import ru.nsu.fit.pak.budle.dto.request.RequestEstablishmentDto;

@Data
public class SpotDto {
    private Long id;
    private String tags;
    private String status;
    private RequestEstablishmentDto establishment;
}

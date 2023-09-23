package ru.nsu.fit.pak.budle.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpotDto {
    private Long id;
    private String tags;
    private String status;
}

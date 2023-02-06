package ru.nsu.fit.pak.budle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentListDto {
    private List<EstablishmentDto> establishments;
    private Integer count;
}

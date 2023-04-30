package ru.nsu.fit.pak.budle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.fit.pak.budle.dto.request.RequestEstablishmentDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentListDto {
    private List<RequestEstablishmentDto> establishments;
    private Integer count;
}

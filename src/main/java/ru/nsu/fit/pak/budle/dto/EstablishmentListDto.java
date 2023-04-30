package ru.nsu.fit.pak.budle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.fit.pak.budle.dto.response.establishment.basic.ResponseBasicEstablishmentInfo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentListDto {
    private List<ResponseBasicEstablishmentInfo> establishments;
    private Integer count;
}

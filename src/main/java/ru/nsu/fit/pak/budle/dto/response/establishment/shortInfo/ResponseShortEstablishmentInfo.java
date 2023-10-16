package ru.nsu.fit.pak.budle.dto.response.establishment.shortInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseShortEstablishmentInfo implements ShortEstablishmentDto {
    private Long id;
    private String name;
}

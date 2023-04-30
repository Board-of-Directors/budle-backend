package ru.nsu.fit.pak.budle.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class RequestHotelDto extends RequestEstablishmentDto {
    @Min(value = 1)
    @Max(value = 5)
    private int starsCount;
}

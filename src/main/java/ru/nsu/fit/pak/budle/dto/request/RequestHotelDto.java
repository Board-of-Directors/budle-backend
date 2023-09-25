package ru.nsu.fit.pak.budle.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class RequestHotelDto extends RequestEstablishmentDto {
    @Min(value = 1, message = "Количество звезд не может быть меньше 1.")
    @Max(value = 5, message = "Количество звезд не может быть больше 5.")
    @NotNull(message = "Количество звезд не может быть не задано.")
    private int starsCount;
}

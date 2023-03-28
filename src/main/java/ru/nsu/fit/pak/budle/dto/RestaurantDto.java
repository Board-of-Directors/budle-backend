package ru.nsu.fit.pak.budle.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RestaurantDto extends EstablishmentDto {
    @NotNull
    private String cuisineCountry;
}

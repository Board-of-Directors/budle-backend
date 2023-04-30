package ru.nsu.fit.pak.budle.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestRestaurantDto extends RequestEstablishmentDto {
    @NotNull
    private String cuisineCountry;
}

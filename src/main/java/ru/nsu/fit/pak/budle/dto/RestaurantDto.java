package ru.nsu.fit.pak.budle.dto;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.CuisineCountry;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RestaurantDto extends EstablishmentDto {
    @NotNull
    private CuisineCountry cuisineCountry;
}

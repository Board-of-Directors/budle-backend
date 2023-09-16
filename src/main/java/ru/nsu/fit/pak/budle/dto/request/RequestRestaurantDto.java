package ru.nsu.fit.pak.budle.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@SuperBuilder
public class RequestRestaurantDto extends RequestEstablishmentDto {
    @NotNull(message = "Информация о кухне ресторана не может быть не задана.")
    private String cuisineCountry;
}

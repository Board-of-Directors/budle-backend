package ru.nsu.fit.pak.budle.dao.establishment.restaurant;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@Getter
@Setter
@DiscriminatorValue(value = "restaurant")
public class Restaurant extends Establishment {
    private final Category category = Category.restaurant;

    private CuisineCountry cuisineCountry;
}

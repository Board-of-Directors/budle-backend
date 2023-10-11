package ru.nsu.fit.pak.budle.dao.establishment.restaurant;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;


@Entity
@Getter
@Setter
@DiscriminatorValue(value = "restaurant")
public class Restaurant extends Establishment {
    private final Category category = Category.restaurant;
    private CuisineCountry cuisineCountry;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "establishment_id")
    private List<MenuCategory> categories;
}

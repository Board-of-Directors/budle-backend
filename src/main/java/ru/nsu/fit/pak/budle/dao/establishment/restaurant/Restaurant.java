package ru.nsu.fit.pak.budle.dao.establishment.restaurant;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import java.util.List;


@Entity
@Getter
@Setter
@DiscriminatorValue(value = "restaurant")
public class Restaurant extends Establishment {
    private final Category category = Category.restaurant;
    @OneToMany
    @JoinColumn(name = "establishment_id")
    private List<MenuCategory> categories;
}

package ru.nsu.fit.pak.budle.dao.establishment.beauty;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "barbershop")
public class Barbershop extends Establishment {
    Category category = Category.barbershop;
}

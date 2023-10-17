package ru.nsu.fit.pak.budle.dao.establishment.beauty;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "barbershop")
public class Barbershop extends Establishment {
    Category category = Category.barbershop;
}

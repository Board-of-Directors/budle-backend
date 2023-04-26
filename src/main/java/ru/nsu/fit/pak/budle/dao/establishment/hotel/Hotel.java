package ru.nsu.fit.pak.budle.dao.establishment.hotel;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "hotel")
public class Hotel extends Establishment {
    private final Category category = Category.hotel;
    private Integer starsCount;
}

package ru.nsu.fit.pak.budle.dao.establishment.hotel;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "hotel")
public class Hotel extends Establishment {
    private final Category category = Category.hotel;
}

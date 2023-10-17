package ru.nsu.fit.pak.budle.dao.establishment.entertainment;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "game_club")
public class GameClub extends Establishment {
    Category category = Category.game_club;

}

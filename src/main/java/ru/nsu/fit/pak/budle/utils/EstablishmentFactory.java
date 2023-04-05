package ru.nsu.fit.pak.budle.utils;

import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dao.establishment.beauty.Barbershop;
import ru.nsu.fit.pak.budle.dao.establishment.entertainment.GameClub;
import ru.nsu.fit.pak.budle.dao.establishment.hotel.Hotel;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.Restaurant;
import ru.nsu.fit.pak.budle.dto.*;
import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that represent establishment factory.
 * Used for reflecting establishment and convert additional fields.
 * Contains two maps: for entities and for dto.
 */
@Component
public class EstablishmentFactory {
    private final Map<String, Establishment> entityFactory;
    private final Map<String, EstablishmentDto> dtoFactory;

    /**
     * Default constructor of establishment factory.
     */
    public EstablishmentFactory() {
        entityFactory = new HashMap<>();
        dtoFactory = new HashMap<>();
        initEntityFactory();
        initDtoFactory();
    }

    /**
     * Initial state of entity factory.
     */
    private void initEntityFactory() {
        entityFactory.put("hotel", new Hotel());
        entityFactory.put("restaurant", new Restaurant());
        entityFactory.put("game_club", new GameClub());
        entityFactory.put("barbershop", new Barbershop());
    }

    /**
     * Initial state of dto factory.
     */
    private void initDtoFactory() {
        dtoFactory.put("hotel", new HotelDto());
        dtoFactory.put("restaurant", new RestaurantDto());
        dtoFactory.put("game_club", new GameClubDto());
        dtoFactory.put("barbershop", new BarbershopDto());
    }

    /**
     * Function that returned class of establishment by name of category.
     *
     * @param type of establishment
     * @return class
     */
    public Class<? extends Establishment> getEstablishmentEntity(String type) {
        Establishment establishment = entityFactory.get(type);
        if (establishment == null) {
            throw new IncorrectDataException();
        }
        return establishment.getClass();
    }

    /**
     * Function that returned class of establishment dto by name of category.
     *
     * @param type of establishment
     * @return class
     */
    public Class<? extends EstablishmentDto> getEstablishmentDto(String type) {
        EstablishmentDto establishmentDto = dtoFactory.get(type);
        if (establishmentDto == null) {
            throw new IncorrectDataException();
        }
        return establishmentDto.getClass();
    }
}

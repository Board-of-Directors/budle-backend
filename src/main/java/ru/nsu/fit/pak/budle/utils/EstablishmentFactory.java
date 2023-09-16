package ru.nsu.fit.pak.budle.utils;

import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dao.establishment.beauty.Barbershop;
import ru.nsu.fit.pak.budle.dao.establishment.entertainment.GameClub;
import ru.nsu.fit.pak.budle.dao.establishment.hotel.Hotel;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.Restaurant;
import ru.nsu.fit.pak.budle.dto.response.establishment.basic.*;
import ru.nsu.fit.pak.budle.dto.response.establishment.extended.*;
import ru.nsu.fit.pak.budle.dto.response.establishment.shortInfo.ResponseShortEstablishmentInfo;
import ru.nsu.fit.pak.budle.exceptions.IncorrectEstablishmentType;

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
    private final Map<String, Map<String, ? extends ResponseShortEstablishmentInfo>> factoryOfDtoFactories;

    /**
     * Default constructor of establishment factory.
     */
    public EstablishmentFactory() {
        entityFactory = getEntityFactory();
        Map<String, ResponseBasicEstablishmentInfo> basicFactory = getBasicDtoFactory();
        Map<String, ResponseExtendedEstablishmentInfo> extendedFactory = getExtendedDtoFactory();
        factoryOfDtoFactories = Map.of("basic", basicFactory, "extended", extendedFactory);
    }

    private Map<String, Establishment> getEntityFactory() {
        return Map.of(
            Category.hotel.value, new Hotel(),
            Category.restaurant.value, new Restaurant(),
            Category.game_club.value, new GameClub(),
            Category.barbershop.value, new Barbershop()
        );
    }

    private Map<String, ResponseBasicEstablishmentInfo> getBasicDtoFactory() {
        return Map.of(
            Category.hotel.name(), new ResponseBasicHotelInfo(),
            Category.restaurant.name(), new ResponseBasicRestaurantInfo(),
            Category.game_club.name(), new ResponseBasicGameClubInfo(),
            Category.barbershop.name(), new ResponseBasicBarbershopInfo()
        );
    }

    private Map<String, ResponseExtendedEstablishmentInfo> getExtendedDtoFactory() {
        return Map.of(
            Category.hotel.name(), new ResponseExtendedHotelInfo(),
            Category.restaurant.name(), new ResponseExtendedRestaurantInfo(),
            Category.game_club.name(), new ResponseExtendedGameClubInfo(),
            Category.barbershop.name(), new ResponseExtendedBarbershopInfo()
        );
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
            throw new IncorrectEstablishmentType();
        }
        return establishment.getClass();
    }

    /**
     * Function that returned class of establishment dto by name of category.
     *
     * @param type of establishment
     * @return class
     */
    public Class<? extends ResponseShortEstablishmentInfo> getEstablishmentDto(String className, String type) {
        ResponseShortEstablishmentInfo establishmentInfo = factoryOfDtoFactories.get(type).get(className);
        if (establishmentInfo == null) {
            throw new IncorrectEstablishmentType();
        }
        return establishmentInfo.getClass();
    }
}

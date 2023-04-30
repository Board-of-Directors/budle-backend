package ru.nsu.fit.pak.budle.utils;

import org.springframework.stereotype.Component;
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
        entityFactory = new HashMap<>();
        factoryOfDtoFactories = new HashMap<>();
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
        Map<String, ResponseBasicEstablishmentInfo> basicFactory = new HashMap<>();
        basicFactory.put("hotel", new ResponseBasicHotelInfo());
        basicFactory.put("restaurant", new ResponseBasicRestaurantInfo());
        basicFactory.put("game_club", new ResponseBasicGameClubInfo());
        basicFactory.put("barbershop", new ResponseBasicBarbershopInfo());
        factoryOfDtoFactories.put("basic", basicFactory);

        Map<String, ResponseExtendedEstablishmentInfo> extendedFactory = new HashMap<>();
        extendedFactory.put("hotel", new ResponseExtendedHotelInfo());
        extendedFactory.put("restaurant", new ResponseExtendedRestaurantInfo());
        extendedFactory.put("game_club", new ResponseExtendedGameClubInfo());
        extendedFactory.put("barbershop", new ResponseExtendedBarbershopInfo());
        factoryOfDtoFactories.put("extended", extendedFactory);
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

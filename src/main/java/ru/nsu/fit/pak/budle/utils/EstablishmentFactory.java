package ru.nsu.fit.pak.budle.utils;

import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dao.establishment.hotel.Hotel;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.Restaurant;
import ru.nsu.fit.pak.budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.budle.dto.HotelDto;
import ru.nsu.fit.pak.budle.dto.RestaurantDto;
import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;

import java.util.HashMap;
import java.util.Map;

public class EstablishmentFactory {
    Map<String, Establishment> entityFactory;
    Map<String, EstablishmentDto> dtoFactory;

    public EstablishmentFactory() {
        entityFactory = new HashMap<>();
        dtoFactory = new HashMap<>();
        initEntityFactory();
        initDtoFactory();
    }

    private void initEntityFactory() {
        entityFactory.put("hotel", new Hotel());
        entityFactory.put("restaurant", new Restaurant());
    }

    private void initDtoFactory() {
        dtoFactory.put("hotel", new HotelDto());
        dtoFactory.put("restaurant", new RestaurantDto());
    }

    public Class<? extends Establishment> getEstablishmentEntity(String type) {
        Establishment establishment = entityFactory.get(type);
        if (establishment == null) {
            throw new IncorrectDataException();
        }
        return establishment.getClass();
    }

    public Class<? extends EstablishmentDto> getEstablishmentDto(String type) {
        EstablishmentDto establishmentDto = dtoFactory.get(type);
        if (establishmentDto == null) {
            throw new IncorrectDataException();
        }
        return establishmentDto.getClass();
    }
}

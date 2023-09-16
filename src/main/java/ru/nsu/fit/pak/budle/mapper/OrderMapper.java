package ru.nsu.fit.pak.budle.mapper;

import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dao.OrderWithSpot;
import ru.nsu.fit.pak.budle.dao.Spot;
import ru.nsu.fit.pak.budle.dto.request.RequestOrderDto;
import ru.nsu.fit.pak.budle.exceptions.SpotNotFoundException;
import ru.nsu.fit.pak.budle.repository.SpotRepository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Component
public class OrderMapper {
    private final ModelMapper mapper;

    @Autowired
    public OrderMapper(ModelMapper mapper, SpotRepository spotRepository) {
        final int bookingDurationMinutes = 240;
        final Converter<Long, Spot> converterToSpot = (src) ->
            (spotRepository.findById(src.getSource())
                .orElseThrow(() -> new SpotNotFoundException(src.getSource())));
        final Converter<LocalDate, Date> converterDate = (src) -> Date.valueOf(src.getSource());

        final Converter<LocalTime, Time> converterToStartTime = (src) ->
            Time.valueOf(src.getSource());

        final Converter<LocalTime, Time> converterTime = (src) ->
            Time.valueOf(src.getSource().plusMinutes(bookingDurationMinutes));

        Condition<Long, Spot> notNull = ctx -> ctx.getSource() != null;

        mapper.createTypeMap(RequestOrderDto.class, OrderWithSpot.class)
            .include(Order.class)
            .addMappings(mapping -> mapping
                .when(notNull)
                .using(converterToSpot)
                .map(
                    RequestOrderDto::getSpotId,
                    OrderWithSpot::setSpot
                ))
            .addMappings(mapping -> mapping.using(converterToStartTime)
                .map(
                    RequestOrderDto::getTime,
                    Order::setStartTime
                ))
            .addMappings(mapping -> mapping.using(converterTime)
                .map(RequestOrderDto::getTime, Order::setEndTime))
            .addMappings(mapping -> mapping.using(converterDate)
                .map(RequestOrderDto::getDate, Order::setDate));
        this.mapper = mapper;
    }

    public Order toEntity(RequestOrderDto dto, Class<? extends Order> mappingClass) {
        return mapper.map(dto, mappingClass);
    }

}

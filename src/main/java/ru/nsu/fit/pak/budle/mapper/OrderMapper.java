package ru.nsu.fit.pak.budle.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dao.Spot;
import ru.nsu.fit.pak.budle.dto.request.RequestOrderDto;
import ru.nsu.fit.pak.budle.exceptions.SpotNotFoundException;
import ru.nsu.fit.pak.budle.repository.SpotRepository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class OrderMapper {
    private final ModelMapper mapper;
    private final SpotRepository spotRepository;

    @Autowired
    public OrderMapper(ModelMapper mapper, SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
        final Converter<Long, Spot> converterToSpot = (src) ->
                (spotRepository.findById(src.getSource())
                        .orElseThrow(() -> new SpotNotFoundException(src.getSource())));
        final Converter<LocalDate, Date> converterDate = (src) -> Date.valueOf(src.getSource());

        final Converter<Time, Time> converterTime = (src) ->
                Time.valueOf(src.getSource().toLocalTime().plus(240, ChronoUnit.MINUTES));


        TypeMap<RequestOrderDto, Order> orderTypeMap = mapper
                .createTypeMap(RequestOrderDto.class, Order.class)
                .addMapping(RequestOrderDto::getTime, Order::setStartTime)
                .addMappings(mapping -> mapping.using(converterTime)
                        .map(RequestOrderDto::getTime,
                                Order::setEndTime))
                .addMappings(mapping -> mapping.using(converterDate).map(
                        RequestOrderDto::getDate,
                        Order::setDate));

        /* mapper.createTypeMap(RequestOrderDto.class, OrderWithSpot.class)
                .include(Order.class)
                .addMappings(mapping -> mapping.using(converterToSpot).map(
                        RequestOrderDto::getSpotId, OrderWithSpot::setSpot));

         */
        this.mapper = mapper;
    }

    public Order toEntity(RequestOrderDto dto, Class<? extends Order> mappingClass) {
        return mapper.map(dto, mappingClass);
    }


}

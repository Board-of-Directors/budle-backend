package ru.nsu.fit.pak.budle.mapper;

import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dao.Spot;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.request.RequestOrderDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseOrderDto;
import ru.nsu.fit.pak.budle.exceptions.SpotNotFoundException;
import ru.nsu.fit.pak.budle.repository.SpotRepository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class OrderMapper {
    private final ModelMapper mapper;
    private final EstablishmentMapper establishmentMapper;

    @Autowired
    public OrderMapper(ModelMapper mapper, SpotRepository spotRepository, EstablishmentMapper establishmentMapper) {
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

        mapper.createTypeMap(RequestOrderDto.class, Order.class)
            .addMappings(mapping -> mapping.when(notNull)
                .using(converterToSpot)
                .map(RequestOrderDto::getSpotId, Order::setSpot)
            )
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
        this.establishmentMapper = establishmentMapper;
    }

    public Order toEntity(RequestOrderDto dto) {
        return mapper.map(dto, Order.class);
    }

    public ResponseOrderDto toResponse(Order order) {
        ResponseOrderDto responseOrderDto = mapper.map(order, ResponseOrderDto.class);
        responseOrderDto.setUsername(order.getUser().getUsername());
        return responseOrderDto;
    }

    public ResponseOrderDto toResponse(Order order, Establishment establishment) {
        ResponseOrderDto responseOrderDto = mapper.map(order, ResponseOrderDto.class);
        responseOrderDto.setEstablishment(establishmentMapper.toBasic(establishment));
        responseOrderDto.setUsername(order.getUser().getUsername());
        return responseOrderDto;
    }
}
